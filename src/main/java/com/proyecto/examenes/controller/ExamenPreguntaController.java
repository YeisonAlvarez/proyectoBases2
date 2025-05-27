package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.ExamenPregunta;
import com.proyecto.examenes.repository.ExamenPreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/examenes/preguntas")
@RequiredArgsConstructor
@CrossOrigin
public class ExamenPreguntaController {

    private final ExamenPreguntaRepository repository;

    @PostMapping
    public ResponseEntity<?> asignarPregunta(@RequestBody ExamenPregunta ep) {
        boolean asignado = repository.asignarPregunta(ep);
        if (asignado) {
            return ResponseEntity.ok(Map.of("message", "Pregunta asignada correctamente"));
        } else {
            return ResponseEntity.status(500).body(Map.of("message", "Error al asignar la pregunta"));
        }
    }

    @GetMapping("/{idExamen}")
    public ResponseEntity<?> obtenerExamenConPreguntas(@PathVariable Long idExamen) {
        return ResponseEntity.ok(repository.obtenerExamenConPreguntas(idExamen));
    }

}
