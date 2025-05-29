package com.proyecto.examenes.repository;


import com.proyecto.examenes.model.ResultadoExamen;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResultadoRepository {
    private final Connection connection;

    public ResultadoRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public List<ResultadoExamen> obtenerResultadosPorEstudiante(Long idEstudiante) {
        List<ResultadoExamen> resultados = new ArrayList<>();

        String sql = "SELECT e.nombre AS nombre_examen, " +
                "SUM(CASE WHEN r.calificacion > 0 THEN 1 ELSE 0 END) AS respuestas_correctas, " +
                "SUM(CASE WHEN r.calificacion = 0 THEN 1 ELSE 0 END) AS respuestas_incorrectas, " +
                "ROUND(SUM(r.calificacion), 2) AS calificacion " +
                "FROM respuestas r " +
                "JOIN examenes e ON r.id_examen = e.id " +
                "WHERE r.id_estudiante = ? " +
                "GROUP BY e.nombre";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ResultadoExamen resultado = new ResultadoExamen();
                resultado.setNombreExamen(rs.getString("nombre_examen"));
                resultado.setRespuestasCorrectas(rs.getInt("respuestas_correctas"));
                resultado.setRespuestasIncorrectas(rs.getInt("respuestas_incorrectas"));
                resultado.setCalificacion(rs.getDouble("calificacion"));

                resultados.add(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }



}

