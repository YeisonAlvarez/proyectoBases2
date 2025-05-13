package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Tema;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TemaRepository {

    private final Connection connection;

    public TemaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crearTema(Tema tema) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO temas (nombre, descripcion, id_curso) VALUES (?, ?, ?)")) {
            stmt.setString(1, tema.getNombre());
            stmt.setString(2, tema.getDescripcion());
            stmt.setLong(3, tema.getIdCurso());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Tema> listarTemas() {
        List<Tema> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM temas")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Tema t = new Tema();
                t.setId(rs.getLong("id"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setIdCurso(rs.getLong("id_curso"));
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
