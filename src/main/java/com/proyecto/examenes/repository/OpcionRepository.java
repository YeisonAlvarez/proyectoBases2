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
        try (CallableStatement stmt = connection.prepareCall("{ call crear_opcion(?, ?, ?, ?,?) }")) {
            stmt.setString(1, opcion.getTexto());
            stmt.setString(2, opcion.getEsCorrecta());
            stmt.setLong(3, opcion.getIdPregunta());

            if (opcion.getIdSubPregunta() != null) {
                stmt.setLong(4, opcion.getIdSubPregunta());
            } else {
                stmt.setNull(4, Types.NUMERIC);  // o Types.NUMERIC según tu BD
            }

            stmt.registerOutParameter(5, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
