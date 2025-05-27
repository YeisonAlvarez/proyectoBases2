package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Pregunta;
import com.proyecto.examenes.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
@CrossOrigin
public class PreguntaController {

    private final PreguntaRepository preguntaRepository;

    @PostMapping
    public ResponseEntity<Long> crearPregunta(@RequestBody Pregunta pregunta) {
        Long id = preguntaRepository.crearPregunta(pregunta);
        if (id != null) {
            return ResponseEntity.ok(id);  // Aquí devuelve directamente el número
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/tema/{idTema}")
    public ResponseEntity<?> obtenerPreguntasPorTema(
            @PathVariable Long idTema,
            @RequestParam(defaultValue = "10") int limite) {

        try {
            var preguntas = preguntaRepository.findPreguntasPorTema(idTema, limite);
            return ResponseEntity.ok(preguntas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al obtener preguntas por tema"));
        }
    }

}
