package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.HorarioClase;
import com.proyecto.examenes.repository.HorarioClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
@CrossOrigin
public class HorarioClaseController {

    private final HorarioClaseRepository repository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HorarioClase h) {
        return repository.crear(h)
                ? ResponseEntity.ok("Horario registrado correctamente")
                : ResponseEntity.status(500).body("Error al registrar horario");
    }

    @GetMapping("/profesor/{id}")
    public List<HorarioClase> porProfesor(@PathVariable Long id) {
        return repository.listarPorProfesor(id);
    }

    @GetMapping("/estudiante/{id}")
    public List<HorarioClase> porEstudiante(@PathVariable Long id) {
        return repository.listarPorEstudiante(id);
    }
}
