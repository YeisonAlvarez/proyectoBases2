package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Grupo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO grupos (id_curso, nombre) VALUES (?, ?)")) {
            stmt.setLong(1, grupo.getIdCurso());
            stmt.setString(2, grupo.getNombre());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
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
}
