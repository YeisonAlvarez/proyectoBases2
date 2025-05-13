package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Opcion;
import com.proyecto.examenes.repository.OpcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                ResponseEntity.ok("Opción creada con ID: " + id) :
                ResponseEntity.status(500).body("Error al crear la opción");
    }
}
