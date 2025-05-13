package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Curso;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {

    private final Connection connection;

    public CursoRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crearCurso(Curso curso) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO cursos (nombre, descripcion) VALUES (?, ?)")) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Curso> listarCursos() {
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cursos")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
