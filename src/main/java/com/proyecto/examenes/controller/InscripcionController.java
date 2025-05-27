package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Inscripcion;
import com.proyecto.examenes.model.Usuario;
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

    @DeleteMapping("/{idGrupo}/{idEstudiante}")
    public ResponseEntity<?> eliminar(@PathVariable Long idGrupo, @PathVariable Long idEstudiante) {
        return repo.eliminarInscripcion(idGrupo, idEstudiante)
                ? ResponseEntity.ok("Inscripción eliminada correctamente")
                : ResponseEntity.status(500).body("Error al eliminar la inscripción");
    }

    @GetMapping("/grupo/{idGrupo}")
    public List<Inscripcion> listarPorGrupo(@PathVariable Long idGrupo) {
        return repo.listarInscripcionesPorGrupo(idGrupo);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public List<Inscripcion> listarPorEstudiante(@PathVariable Long idEstudiante) {
        return repo.listarInscripcionesPorEstudiante(idEstudiante);
    }

    @GetMapping("/grupo/{idGrupo}/estudiantes")
    public List<Usuario> listarEstudiantesPorGrupo(@PathVariable Long idGrupo) {
        return repo.listarEstudiantesPorGrupo(idGrupo);
    }
}
