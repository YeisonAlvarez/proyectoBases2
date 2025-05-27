package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Examen;
import com.proyecto.examenes.model.ExamenConEstado;
import com.proyecto.examenes.model.ResultadoExamen;
import com.proyecto.examenes.repository.ExamenRepository;
import com.proyecto.examenes.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ExamenController {

    private final ExamenRepository quizRepository;
    private final ResultadoRepository resultadoRepository;


        @PostMapping
        public ResponseEntity<?> crearExamen (@RequestBody Examen examen){
            Long id = quizRepository.crearQuiz(examen);
            if (id != null) {
                return ResponseEntity.ok(id);  // Se asegura de devolver un mensaje adecuado
            } else {
                return ResponseEntity.status(500).body("Error al crear el examen");
            }
        }
        @GetMapping("/disponibles/{idEstudiante}")
        public List<Examen> obtenerExamenesDisponibles (@PathVariable Long idEstudiante){
            return quizRepository.buscarExamenesDisponibles(idEstudiante);
        }

        @GetMapping("/resultados")
        public List<ResultadoExamen> obtenerResultados (@RequestParam Long idEstudiante){
            return resultadoRepository.obtenerResultadosPorEstudiante(idEstudiante);
        }


    @GetMapping("/disponibles-con-estado/{idEstudiante}")
    public ResponseEntity<List<ExamenConEstado>> obtenerExamenesConEstado(@PathVariable Long idEstudiante) {
        try {
            List<ExamenConEstado> examenes = quizRepository.buscarExamenesConEstado(idEstudiante);
            return ResponseEntity.ok(examenes);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}

