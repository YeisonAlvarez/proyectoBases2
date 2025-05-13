package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Unidad;
import com.proyecto.examenes.repository.UnidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
@CrossOrigin
public class UnidadController {

    private final UnidadRepository repository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Unidad unidad) {
        return repository.crear(unidad)
                ? ResponseEntity.ok("Unidad creada correctamente")
                : ResponseEntity.status(500).body("Error al crear unidad");
    }

    @GetMapping
    public List<Unidad> listar() {
        return repository.listar();
    }
}
