package com.proyecto.examenes.controller;

import com.proyecto.examenes.dto.RespuestaEstudianteDTO;
import com.proyecto.examenes.service.PresentacionExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/examenes/presentar")
@RequiredArgsConstructor
@CrossOrigin
public class PresentacionExamenController {

    private final PresentacionExamenService service;

    @PostMapping
    public ResponseEntity<?> presentar(@RequestBody RespuestaEstudianteDTO dto) {
        double notaTotal = service.enviarRespuestas(dto); // Cambiar para que devuelva nota total
        if (notaTotal >= 0) {
            return ResponseEntity.ok(Map.of("notaTotal", notaTotal));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "Error al presentar el examen"));
        }
    }
}
