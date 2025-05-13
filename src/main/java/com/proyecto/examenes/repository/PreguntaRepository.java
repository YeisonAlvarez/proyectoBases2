package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Pregunta;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class  PreguntaRepository {

    private final Connection connection;

    public PreguntaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearPregunta(Pregunta pregunta) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_pregunta(?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {
            stmt.setString(1, pregunta.getTexto());
            stmt.setString(2, pregunta.getTipo());
            stmt.setDouble(3, pregunta.getPorcentaje());
            stmt.setString(4, pregunta.getDificultad());
            if (pregunta.getTiempoLimite() != null) {
                stmt.setInt(5, pregunta.getTiempoLimite());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.setString(6, pregunta.getEsPublica());
            stmt.setLong(7, pregunta.getIdTema());
            stmt.setLong(8, pregunta.getIdProfesor());
            stmt.registerOutParameter(9, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(9);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
