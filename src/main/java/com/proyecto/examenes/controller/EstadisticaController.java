package com.proyecto.examenes.controller;

import com.proyecto.examenes.service.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@CrossOrigin
public class EstadisticaController {

    private final EstadisticaService service;

    @GetMapping("/examen/{id}")
    public ResponseEntity<?> obtenerEstadisticaExamen(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerEstadisticaExamen(id));
    }
}
