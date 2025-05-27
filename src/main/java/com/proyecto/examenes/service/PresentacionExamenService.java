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

    public double enviarRespuestas(RespuestaEstudianteDTO dto) {
        double total = 0;
        for (RespuestaIndividualDTO r : dto.getRespuestas()) {
            try (CallableStatement stmt = connection.prepareCall("{ call EXAMENES.registrar_respuesta(?, ?, ?, ?, ?) }")) {
                stmt.setLong(1, dto.getIdEstudiante());
                stmt.setLong(2, dto.getIdExamen());
                stmt.setLong(3, r.getIdPregunta());
                stmt.setString(4, r.getRespuestaTexto());
                stmt.registerOutParameter(5, Types.NUMERIC);

                stmt.execute();
                total += stmt.getDouble(5); // calificación individual
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // error
            }
        }
       // se hace usuo del trg_validar_nota_resultado despues de INSERT OR UPDATE ON resultados (la nota no supere 5)

        return total;
    }

}
