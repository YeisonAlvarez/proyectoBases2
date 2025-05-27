package com.proyecto.examenes.repository;
import com.proyecto.examenes.model.ExamenGrupo;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ExamenGrupoRepository {

    private final Connection connection;

    public ExamenGrupoRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean asignarGrupo(ExamenGrupo eg) {
        try (CallableStatement stmt = connection.prepareCall("{ call asignar_examen_grupo(?, ?) }")) {
            stmt.setLong(1, eg.getIdExamen());
            stmt.setLong(2, eg.getIdGrupo());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}