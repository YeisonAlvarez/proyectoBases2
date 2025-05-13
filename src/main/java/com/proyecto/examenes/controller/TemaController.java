package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Tema;
import com.proyecto.examenes.repository.TemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
@RequiredArgsConstructor
@CrossOrigin
public class TemaController {

    private final TemaRepository temaRepository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Tema tema) {
        return temaRepository.crearTema(tema)
                ? ResponseEntity.ok("Tema creado correctamente")
                : ResponseEntity.status(500).body("Error al crear el tema");
    }

    @GetMapping
    public List<Tema> listar() {
        return temaRepository.listarTemas();
    }
}
