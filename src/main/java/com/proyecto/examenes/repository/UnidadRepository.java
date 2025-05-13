package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Unidad;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UnidadRepository {

    private final Connection connection;

    public UnidadRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crear(Unidad unidad) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO unidades (nombre, numero, id_plan_estudio) VALUES (?, ?, ?)")) {
            stmt.setString(1, unidad.getNombre());
            stmt.setInt(2, unidad.getNumero());
            stmt.setLong(3, unidad.getIdPlanEstudio());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Unidad> listar() {
        List<Unidad> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM unidades")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Unidad u = new Unidad();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setNumero(rs.getInt("numero"));
                u.setIdPlanEstudio(rs.getLong("id_plan_estudio"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
