package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Opcion;
import com.proyecto.examenes.repository.OpcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/opciones")
@RequiredArgsConstructor
@CrossOrigin
public class OpcionController {

    private final OpcionRepository opcionRepository;

    @PostMapping
    public ResponseEntity<?> crearOpcion(@RequestBody Opcion opcion) {
        Long id = opcionRepository.crearOpcion(opcion);
        return id != null ?
                ResponseEntity.ok(Map.of("id", id, "mensaje", "Opción creada correctamente")) :
                ResponseEntity.status(500).body(Map.of("error", "Error al crear la opción"));
    }
}