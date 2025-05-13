package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Opcion;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class OpcionRepository {

    private final Connection connection;

    public OpcionRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearOpcion(Opcion opcion) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_opcion(?, ?, ?, ?) }")) {
            stmt.setString(1, opcion.getTexto());
            stmt.setString(2, opcion.getEsCorrecta());
            stmt.setLong(3, opcion.getIdPregunta());
            stmt.registerOutParameter(4, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(4);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
