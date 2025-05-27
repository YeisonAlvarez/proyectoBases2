package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Subpregunta;
import com.proyecto.examenes.repository.SubpreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subpreguntas")
@RequiredArgsConstructor
@CrossOrigin
public class SubpreguntaController {

    private final SubpreguntaRepository repository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Subpregunta sub) {
        Long id = repository.crearSubpregunta(sub);
        return id != null
                ? ResponseEntity.ok( id)
                : ResponseEntity.status(400).body("Error: suma de porcentajes supera 100%");
    }
}
