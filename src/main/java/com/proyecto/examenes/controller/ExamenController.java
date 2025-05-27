package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Examen;
import com.proyecto.examenes.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@CrossOrigin
public class ExamenController {

    private final ExamenRepository quizRepository;

    @PostMapping
    public ResponseEntity<?> crearExamen(@RequestBody Examen examen) {
        Long id = quizRepository.crearQuiz(examen);
        if (id != null) {
            return ResponseEntity.ok(id);  // Se asegura de devolver un mensaje adecuado
        } else {
            return ResponseEntity.status(500).body("Error al crear el examen");
        }
    }
    @GetMapping("/disponibles/{idEstudiante}")
    public List<Examen> obtenerExamenesDisponibles(@PathVariable Long idEstudiante) {
        return quizRepository.buscarExamenesDisponibles(idEstudiante);
    }

}
