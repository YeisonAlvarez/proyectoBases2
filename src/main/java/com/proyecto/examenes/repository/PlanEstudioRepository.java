package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.PlanEstudio;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlanEstudioRepository {

    private final Connection connection;

    public PlanEstudioRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean crear(PlanEstudio plan) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO planes_estudio (id_curso, titulo, descripcion) VALUES (?, ?, ?)")) {
            stmt.setLong(1, plan.getIdCurso());
            stmt.setString(2, plan.getTitulo());
            stmt.setString(3, plan.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PlanEstudio> listar() {
        List<PlanEstudio> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM planes_estudio")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PlanEstudio plan = new PlanEstudio();
                plan.setId(rs.getLong("id"));
                plan.setIdCurso(rs.getLong("id_curso"));
                plan.setTitulo(rs.getString("titulo"));
                plan.setDescripcion(rs.getString("descripcion"));
                lista.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
