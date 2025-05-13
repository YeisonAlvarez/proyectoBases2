package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Pregunta;
import com.proyecto.examenes.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
@CrossOrigin
public class PreguntaController {

    private final PreguntaRepository preguntaRepository;

    @PostMapping
    public ResponseEntity<?> crearPregunta(@RequestBody Pregunta pregunta) {
        Long id = preguntaRepository.crearPregunta(pregunta);
        return id != null ?
                ResponseEntity.ok("Pregunta creada con ID: " + id) :
                ResponseEntity.status(500).body("Error al crear la pregunta");
    }
}
