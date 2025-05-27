package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Examen;
import com.proyecto.examenes.model.ExamenConEstado;
import com.proyecto.examenes.model.ResultadoExamen;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamenRepository {

    private final Connection connection;

    public ExamenRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearQuiz(Examen quiz) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_examen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {
            stmt.setString(1, quiz.getNombre());
            stmt.setString(2, quiz.getDescripcion());
            stmt.setInt(3, quiz.getIdTema());
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

    public List<Examen> buscarExamenesDisponibles(Long idEstudiante) {

        List<Examen> examenes = new ArrayList<>();
        String sql = "SELECT e.* FROM examenes e JOIN examen_grupo eg ON e.id = eg.id_examen JOIN grupo_estudiante ge ON eg.id_grupo = ge.id_grupo  WHERE ge.id_estudiante = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Examen examen = new Examen();
                examen.setId(rs.getLong("id"));
                examen.setNombre(rs.getString("nombre"));
                examen.setDescripcion(rs.getString("descripcion"));
                examen.setIdTema(rs.getInt("id_tema"));
                examen.setTotalPreguntasBanco(rs.getInt("total_preguntas_banco"));
                examen.setTotalPreguntasExamen(rs.getInt("total_preguntas_examen"));
                examen.setDuracionMinutos(rs.getInt("duracion_minutos"));
                examen.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
                examen.setFechaFin(rs.getTimestamp("fecha_fin").toLocalDateTime());
                examen.setIdProfesor(rs.getLong("id_profesor"));

                examenes.add(examen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return examenes;
    }


    public List<ExamenConEstado> buscarExamenesConEstado(Long idEstudiante) throws SQLException {
        List<ExamenConEstado> examenes = new ArrayList<>();

        String sql = """
                    SELECT e.id, e.nombre, e.descripcion,
                           CASE WHEN r.id_examen IS NOT NULL THEN 1 ELSE 0 END AS presentado
                    FROM examenes e
                    LEFT JOIN resultados r ON e.id = r.id_examen AND r.id_estudiante = ?
                    WHERE e.fecha_inicio <= SYSDATE AND e.fecha_fin >= SYSDATE
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ExamenConEstado ex = new ExamenConEstado();
                ex.setId(rs.getLong("id"));
                ex.setNombre(rs.getString("nombre"));
                ex.setDescripcion(rs.getString("descripcion"));
                ex.setPresentado(rs.getInt("presentado") == 1);
                examenes.add(ex);
            }
        }

        return examenes;
    }

}
