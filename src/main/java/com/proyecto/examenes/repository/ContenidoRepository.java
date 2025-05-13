package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Contenido;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContenidoRepository {

    private final Connection connection;

    public ContenidoRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crear(Contenido c) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO contenidos (descripcion, id_unidad) VALUES (?, ?)")) {
            stmt.setString(1, c.getDescripcion());
            stmt.setLong(2, c.getIdUnidad());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Contenido> listar() {
        List<Contenido> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contenidos")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contenido c = new Contenido();
                c.setId(rs.getLong("id"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setIdUnidad(rs.getLong("id_unidad"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
