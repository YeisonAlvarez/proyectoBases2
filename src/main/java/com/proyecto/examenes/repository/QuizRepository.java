package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Quiz;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.ZoneId;

@Repository
public class QuizRepository {

    private final Connection connection;

    public QuizRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearQuiz(Quiz quiz) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_examen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {
            stmt.setString(1, quiz.getNombre());
            stmt.setString(2, quiz.getDescripcion());
            stmt.setString(3, quiz.getCategoria());
            stmt.setInt(4, quiz.getTotalPreguntasBanco());
            stmt.setInt(5, quiz.getTotalPreguntasExamen());
            stmt.setInt(6, quiz.getDuracionMinutos());
            stmt.setTimestamp(7, Timestamp.valueOf(quiz.getFechaInicio()));
            stmt.setTimestamp(8, Timestamp.valueOf(quiz.getFechaFin()));
            stmt.setLong(9, quiz.getIdProfesor());
            stmt.registerOutParameter(10, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(10);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
