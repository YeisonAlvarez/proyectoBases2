package com.proyecto.examenes.service;

import com.proyecto.examenes.model.Usuario;
import com.proyecto.examenes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String correo, String contrasena) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin")) {
            CallableStatement stmt = connection.prepareCall("{ call autenticar_usuario(?, ?, ?, ?, ?, ?) }");
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
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
