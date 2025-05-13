package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.ExamenPregunta;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ExamenPreguntaRepository {

    private final Connection connection;

    public ExamenPreguntaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean asignarPregunta(ExamenPregunta ep) {
        try (CallableStatement stmt = connection.prepareCall("{ call asignar_pregunta_examen(?, ?, ?) }")) {
            stmt.setLong(1, ep.getIdExamen());
            stmt.setLong(2, ep.getIdPregunta());
            stmt.setDouble(3, ep.getPorcentaje());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
