package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    private final Connection connection;

    public UsuarioRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Optional<Usuario> autenticar(String correo, String contrasena) {
        try (CallableStatement stmt = connection.prepareCall("{ call autenticar_usuario(?, ?, ?, ?, ?, ?) }")) {
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            stmt.registerOutParameter(3, Types.NUMERIC); // id
            stmt.registerOutParameter(4, Types.VARCHAR); // nombre
            stmt.registerOutParameter(5, Types.VARCHAR); // rol
            stmt.registerOutParameter(6, Types.INTEGER); // encontrado

            stmt.execute();

            int encontrado = stmt.getInt(6);

            if (encontrado == 1) {
                Usuario usuario = new Usuario();
                usuario.setId(stmt.getLong(3));
                usuario.setNombre(stmt.getString(4));
                usuario.setCorreo(correo);
                usuario.setContrasena(contrasena);
                usuario.setRol(stmt.getString(5));
                System.out.println("Login exitoso: " + usuario.getNombre());
                return Optional.of(usuario);
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean crearUsuario(Usuario u) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO usuarios (nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM usuarios")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizarUsuario(Long id, Usuario u) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE usuarios SET nombre=?, correo=?, contrasena=?, rol=? WHERE id=?")) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getRol());
            stmt.setLong(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(Long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM usuarios WHERE id=?")) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
