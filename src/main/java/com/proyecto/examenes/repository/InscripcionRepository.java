package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Inscripcion;
import com.proyecto.examenes.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InscripcionRepository {

    private final Connection connection;

    public InscripcionRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean inscribir(Inscripcion ins) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO grupo_estudiante (id_grupo, id_estudiante) VALUES (?, ?)")) {
            stmt.setLong(1, ins.getIdGrupo());
            stmt.setLong(2, ins.getIdEstudiante());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Inscripcion> listarInscripciones() {
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM grupo_estudiante")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscripcion i = new Inscripcion();
                i.setIdGrupo(rs.getLong("id_grupo"));
                i.setIdEstudiante(rs.getLong("id_estudiante"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarInscripcion(Long idGrupo, Long idEstudiante) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM grupo_estudiante WHERE id_grupo = ? AND id_estudiante = ?")) {
            stmt.setLong(1, idGrupo);
            stmt.setLong(2, idEstudiante);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Inscripcion> listarInscripcionesPorGrupo(Long idGrupo) {
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM grupo_estudiante WHERE id_grupo = ?")) {
            stmt.setLong(1, idGrupo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscripcion i = new Inscripcion();
                i.setIdGrupo(rs.getLong("id_grupo"));
                i.setIdEstudiante(rs.getLong("id_estudiante"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Inscripcion> listarInscripcionesPorEstudiante(Long idEstudiante) {
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM grupo_estudiante WHERE id_estudiante = ?")) {
            stmt.setLong(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscripcion i = new Inscripcion();
                i.setIdGrupo(rs.getLong("id_grupo"));
                i.setIdEstudiante(rs.getLong("id_estudiante"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> listarEstudiantesPorGrupo(Long idGrupo) {
        List<Usuario> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT u.* FROM usuarios u " +
                "JOIN grupo_estudiante ge ON u.id = ge.id_estudiante " +
                "WHERE ge.id_grupo = ? AND u.rol = 'ESTUDIANTE'")) {
            stmt.setLong(1, idGrupo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
