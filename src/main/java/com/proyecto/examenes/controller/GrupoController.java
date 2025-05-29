package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Grupo;
import com.proyecto.examenes.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
@CrossOrigin
public class GrupoController {

    private final GrupoRepository grupoRepository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Grupo grupo) {
        return grupoRepository.crearGrupo(grupo)
                ? ResponseEntity.ok(java.util.Map.of("message", "Grupo creado correctamente"))
                : ResponseEntity.status(500).body(java.util.Map.of("message", "Error al crear el grupo"));
    }

    @GetMapping
    public List<Grupo> listar() {
        return grupoRepository.listarGrupos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return grupoRepository.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Grupo grupo) {
        return grupoRepository.actualizarGrupo(id, grupo)
                ? ResponseEntity.ok(java.util.Map.of("message", "Grupo actualizado correctamente"))
                : ResponseEntity.status(500).body(java.util.Map.of("message", "Error al actualizar el grupo"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return grupoRepository.eliminarGrupo(id)
                ? ResponseEntity.ok(java.util.Map.of("message", "Grupo eliminado correctamente"))
                : ResponseEntity.status(500).body(java.util.Map.of("message", "Error al eliminar el grupo"));
    }

    @GetMapping("/curso/{idCurso}")
    public List<Grupo> listarPorCurso(@PathVariable Long idCurso) {
        return grupoRepository.listarGruposPorCurso(idCurso);
    }
}
