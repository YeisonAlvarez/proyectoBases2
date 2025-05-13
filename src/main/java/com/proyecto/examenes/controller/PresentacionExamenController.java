package com.proyecto.examenes.controller;

import com.proyecto.examenes.dto.RespuestaEstudianteDTO;
import com.proyecto.examenes.service.PresentacionExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examenes/presentar")
@RequiredArgsConstructor
@CrossOrigin
public class PresentacionExamenController {

    private final PresentacionExamenService service;

    @PostMapping
    public ResponseEntity<?> presentar(@RequestBody RespuestaEstudianteDTO dto) {
        boolean exito = service.enviarRespuestas(dto);
        return exito ?
                ResponseEntity.ok("Examen presentado con éxito") :
                ResponseEntity.status(500).body("Error al presentar el examen");
    }
}
