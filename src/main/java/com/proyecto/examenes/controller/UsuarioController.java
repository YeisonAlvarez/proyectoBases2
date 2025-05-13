package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Usuario;
import com.proyecto.examenes.dto.LoginRequest;
import com.proyecto.examenes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return usuarioRepository.autenticar(request.getCorreo(), request.getContrasena())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).body("Credenciales inválidas"));
    }


    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario u) {
        return usuarioRepository.crearUsuario(u)
                ? ResponseEntity.ok("Usuario creado correctamente")
                : ResponseEntity.status(500).body("Error al crear usuario");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario u) {
        return usuarioRepository.actualizarUsuario(id, u)
                ? ResponseEntity.ok("Usuario actualizado correctamente")
                : ResponseEntity.status(500).body("Error al actualizar usuario");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return usuarioRepository.eliminarUsuario(id)
                ? ResponseEntity.ok("Usuario eliminado correctamente")
                : ResponseEntity.status(500).body("Error al eliminar usuario");
    }


}
