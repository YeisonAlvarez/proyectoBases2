package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Quiz;
import com.proyecto.examenes.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@CrossOrigin
public class QuizController {

    private final QuizRepository quizRepository;

    @PostMapping
    public ResponseEntity<?> crearQuiz(@RequestBody Quiz quiz) {
        Long id = quizRepository.crearQuiz(quiz);
        if (id != null) {
            return ResponseEntity.ok("Quiz creado con ID: " + id);  // Se asegura de devolver un mensaje adecuado
        } else {
            return ResponseEntity.status(500).body("Error al crear el quiz");
        }
    }


}
