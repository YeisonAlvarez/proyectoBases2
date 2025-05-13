package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Inscripcion;
import com.proyecto.examenes.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@CrossOrigin
public class InscripcionController {

    private final InscripcionRepository repo;

    @PostMapping
    public ResponseEntity<?> inscribir(@RequestBody Inscripcion ins) {
        return repo.inscribir(ins)
                ? ResponseEntity.ok("Inscripción realizada correctamente")
                : ResponseEntity.status(500).body("Error al inscribir");
    }

    @GetMapping
    public List<Inscripcion> listar() {
        return repo.listarInscripciones();
    }
}
