package com.proyecto.examenes.service;

import com.proyecto.examenes.dto.EstadisticaExamenDTO;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class EstadisticaService {

    private final Connection connection;

    public EstadisticaService() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public EstadisticaExamenDTO obtenerEstadisticaExamen(Long idExamen) {
        EstadisticaExamenDTO dto = new EstadisticaExamenDTO();
        dto.setIdExamen(idExamen);

        try (CallableStatement stmt = connection.prepareCall("{ call estadisticas_examen(?, ?, ?, ?, ?) }")) {
            stmt.setLong(1, idExamen);
            stmt.registerOutParameter(2, Types.INTEGER); // total presentados
            stmt.registerOutParameter(3, Types.DOUBLE);  // promedio
            stmt.registerOutParameter(4, Types.DOUBLE);  // maximo
            stmt.registerOutParameter(5, Types.DOUBLE);  // minimo

            stmt.execute();

            dto.setTotalPresentados(stmt.getInt(2));
            dto.setPromedio(stmt.getDouble(3));
            dto.setMaximo(stmt.getDouble(4));
            dto.setMinimo(stmt.getDouble(5));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }
}
