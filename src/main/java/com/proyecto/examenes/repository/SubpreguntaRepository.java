package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Subpregunta;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class SubpreguntaRepository {

    private final Connection connection;

    public SubpreguntaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearSubpregunta(Subpregunta sub) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_subpregunta(?, ?, ?, ?) }")) {
            stmt.setString(1, sub.getTexto());
            stmt.setDouble(2, sub.getPorcentaje());
            stmt.setLong(3, sub.getIdPreguntaPadre());
            stmt.registerOutParameter(4, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(4);
        } catch (SQLException e) {
            System.err.println("ERROR AL CREAR SUBPREGUNTA: " + e.getMessage());
            return null;
        }
    }
}
