package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Curso;
import com.proyecto.examenes.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin
public class CursoController {

    private final CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        return cursoRepository.crearCurso(curso)
                ? ResponseEntity.ok("Curso creado correctamente")
                : ResponseEntity.status(500).body("Error al crear el curso");
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoRepository.listarCursos();
    }
}
