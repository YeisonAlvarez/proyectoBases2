package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.ExamenGrupo;
import com.proyecto.examenes.repository.ExamenGrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/examenes/grupos")
@RequiredArgsConstructor
@CrossOrigin
public class ExamenGrupoController {

    private final ExamenGrupoRepository repository;

    @PostMapping
    public ResponseEntity<?> asignarGrupo(@RequestBody ExamenGrupo eg) {
        boolean asignado = repository.asignarGrupo(eg);
        if (asignado) {
            return ResponseEntity.ok(Map.of("message", "Grupo asignado correctamente"));
        } else {
            return ResponseEntity.status(500).body(Map.of("message", "Error al asignar el grupo"));
        }
    }
}
