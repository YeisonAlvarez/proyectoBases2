package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.ExamenPregunta;
import com.proyecto.examenes.repository.ExamenPreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examenes/preguntas")
@RequiredArgsConstructor
@CrossOrigin
public class ExamenPreguntaController {

    private final ExamenPreguntaRepository repository;

    @PostMapping
    public ResponseEntity<?> asignarPregunta(@RequestBody ExamenPregunta ep) {
        boolean asignado = repository.asignarPregunta(ep);
        return asignado ?
                ResponseEntity.ok("Pregunta asignada correctamente") :
                ResponseEntity.status(500).body("Error al asignar la pregunta");
    }
}
