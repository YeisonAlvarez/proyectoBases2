package com.proyecto.examenes.controller;

import com.proyecto.examenes.service.EstadisticasPreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas/preguntas")
@RequiredArgsConstructor
@CrossOrigin
public class EstadisticasPreguntaController {

    private final EstadisticasPreguntaService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerEstadisticas(id));
    }
}
