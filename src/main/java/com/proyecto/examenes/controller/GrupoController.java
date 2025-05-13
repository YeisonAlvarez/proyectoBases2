package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Grupo;
import com.proyecto.examenes.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
@CrossOrigin
public class GrupoController {

    private final GrupoRepository grupoRepository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Grupo grupo) {
        return grupoRepository.crearGrupo(grupo)
                ? ResponseEntity.ok("Grupo creado correctamente")
                : ResponseEntity.status(500).body("Error al crear el grupo");
    }

    @GetMapping
    public List<Grupo> listar() {
        return grupoRepository.listarGrupos();
    }
}
