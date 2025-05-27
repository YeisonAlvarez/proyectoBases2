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

        String sql = "select * from temas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Recorremos todos los registros del ResultSet
            while (rs.next()) {

                Tema t = new Tema();
                t.setId(rs.getLong("id"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setIdCurso(rs.getLong("id_curso"));
                lista.add(t); // Agregamos el tema a la lista
            }

        } catch (SQLException e) {
            // Mejor manejar el error con un log o lanzar excepción personalizada
            System.err.println("Error al listar temas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

}
