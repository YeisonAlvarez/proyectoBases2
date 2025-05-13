package com.proyecto.examenes.service;

import com.proyecto.examenes.dto.RespuestaEstudianteDTO;
import com.proyecto.examenes.dto.RespuestaIndividualDTO;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class PresentacionExamenService {

    private final Connection connection;

    public PresentacionExamenService() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean enviarRespuestas(RespuestaEstudianteDTO dto) {
        double total = 0;
        for (RespuestaIndividualDTO r : dto.getRespuestas()) {
            try (CallableStatement stmt = connection.prepareCall("{ call registrar_respuesta(?, ?, ?, ?, ?) }")) {
                stmt.setLong(1, dto.getIdEstudiante());
                stmt.setLong(2, dto.getIdExamen());
                stmt.setLong(3, r.getIdPregunta());
                stmt.setString(4, r.getRespuestaTexto());
                stmt.registerOutParameter(5, Types.NUMERIC);

                stmt.execute();
                total += stmt.getDouble(5); // calificación individual
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Guardar nota total
        try (CallableStatement stmt = connection.prepareCall("{ call guardar_resultado_examen(?, ?, ?, ?) }")) {
            stmt.setLong(1, dto.getIdEstudiante());
            stmt.setLong(2, dto.getIdExamen());
            stmt.setDouble(3, total);
            stmt.setString(4, dto.getIpOrigen());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
