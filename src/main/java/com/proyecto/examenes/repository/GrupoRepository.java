package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Grupo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GrupoRepository {

    private final Connection connection;

    public GrupoRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crearGrupo(Grupo grupo) {
        System.out.println("Recibido grupo: nombre=" + grupo.getNombre() + ", idCurso=" + grupo.getIdCurso());
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO grupos (id_curso, nombre) VALUES (?, ?)")) {
            stmt.setLong(1, grupo.getIdCurso());
            stmt.setString(2, grupo.getNombre());
            boolean result = stmt.executeUpdate() > 0;
            System.out.println("Resultado de insert: " + result);
            return result;
        } catch (SQLException e) {
            System.out.println("Error SQL al crear grupo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Grupo> listarGrupos() {
        List<Grupo> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM grupos")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grupo g = new Grupo();
                g.setId(rs.getLong("id"));
                g.setIdCurso(rs.getLong("id_curso"));
                g.setNombre(rs.getString("nombre"));
                lista.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Optional<Grupo> buscarPorId(Long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM grupos WHERE id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Grupo g = new Grupo();
                g.setId(rs.getLong("id"));
                g.setIdCurso(rs.getLong("id_curso"));
                g.setNombre(rs.getString("nombre"));
                return Optional.of(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean actualizarGrupo(Long id, Grupo grupo) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE grupos SET id_curso = ?, nombre = ? WHERE id = ?")) {
            stmt.setLong(1, grupo.getIdCurso());
            stmt.setString(2, grupo.getNombre());
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarGrupo(Long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM grupos WHERE id = ?")) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Grupo> listarGruposPorCurso(Long idCurso) {
        List<Grupo> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM grupos WHERE id_curso = ?")) {
            stmt.setLong(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grupo g = new Grupo();
                g.setId(rs.getLong("id"));
                g.setIdCurso(rs.getLong("id_curso"));
                g.setNombre(rs.getString("nombre"));
                lista.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
