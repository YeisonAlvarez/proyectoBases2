package com.proyecto.examenes.service;

import com.proyecto.examenes.dto.EstadisticaPreguntaDTO;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class EstadisticasPreguntaService {

    private final Connection connection;

    public EstadisticasPreguntaService() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public EstadisticaPreguntaDTO obtenerEstadisticas(Long idPregunta) {
        EstadisticaPreguntaDTO dto = new EstadisticaPreguntaDTO();
        dto.setIdPregunta(idPregunta);

        try (CallableStatement stmt = connection.prepareCall("{ call estadisticas_pregunta(?, ?, ?, ?, ?, ?) }")) {
            stmt.setLong(1, idPregunta);
            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.registerOutParameter(5, Types.INTEGER);
            stmt.registerOutParameter(6, Types.DOUBLE);

            stmt.execute();

            dto.setTextoPregunta(stmt.getString(2));
            dto.setTotalRespuestas(stmt.getInt(3));
            dto.setCorrectas(stmt.getInt(4));
            dto.setIncorrectas(stmt.getInt(5));
            dto.setPorcentajeAcierto(stmt.getDouble(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }
}
