package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.HorarioClase;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HorarioClaseRepository {

    private final Connection connection;

    public HorarioClaseRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crear(HorarioClase horario) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO horarios_clase (id_grupo, id_profesor, dia_semana, hora_inicio, hora_fin, aula) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setLong(1, horario.getIdGrupo());
            stmt.setLong(2, horario.getIdProfesor());
            stmt.setString(3, horario.getDiaSemana());
            stmt.setString(4, horario.getHoraInicio());
            stmt.setString(5, horario.getHoraFin());
            stmt.setString(6, horario.getAula());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HorarioClase> listarPorProfesor(Long idProfesor) {
        List<HorarioClase> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM horarios_clase WHERE id_profesor = ?")) {
            stmt.setLong(1, idProfesor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HorarioClase h = new HorarioClase();
                h.setId(rs.getLong("id"));
                h.setIdGrupo(rs.getLong("id_grupo"));
                h.setIdProfesor(rs.getLong("id_profesor"));
                h.setDiaSemana(rs.getString("dia_semana"));
                h.setHoraInicio(rs.getString("hora_inicio"));
                h.setHoraFin(rs.getString("hora_fin"));
                h.setAula(rs.getString("aula"));
                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<HorarioClase> listarPorEstudiante(Long idEstudiante) {
        List<HorarioClase> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT h.* FROM horarios_clase h " +
                        "JOIN grupo_estudiante ge ON h.id_grupo = ge.id_grupo " +
                        "WHERE ge.id_estudiante = ?")) {
            stmt.setLong(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HorarioClase h = new HorarioClase();
                h.setId(rs.getLong("id"));
                h.setIdGrupo(rs.getLong("id_grupo"));
                h.setIdProfesor(rs.getLong("id_profesor"));
                h.setDiaSemana(rs.getString("dia_semana"));
                h.setHoraInicio(rs.getString("hora_inicio"));
                h.setHoraFin(rs.getString("hora_fin"));
                h.setAula(rs.getString("aula"));
                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
