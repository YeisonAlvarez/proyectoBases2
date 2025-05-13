package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Contenido;
import com.proyecto.examenes.repository.ContenidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
@CrossOrigin
public class ContenidoController {

    private final ContenidoRepository repository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Contenido contenido) {
        return repository.crear(contenido)
                ? ResponseEntity.ok("Contenido creado correctamente")
                : ResponseEntity.status(500).body("Error al crear contenido");
    }

    @GetMapping
    public List<Contenido> listar() {
        return repository.listar();
    }
}
