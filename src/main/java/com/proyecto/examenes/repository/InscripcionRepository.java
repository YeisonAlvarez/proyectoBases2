package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Inscripcion;
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
}
