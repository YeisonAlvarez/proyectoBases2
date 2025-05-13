package com.proyecto.examenes.controller;

import com.proyecto.examenes.service.ReporteNotasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@CrossOrigin
public class ReporteNotasController {

    private final ReporteNotasService service;

    @GetMapping("/notas/{idExamen}")
    public ResponseEntity<byte[]> descargarExcel(@PathVariable Long idExamen) {
        byte[] archivo = service.generarExcelNotas(idExamen);
        if (archivo == null) {
            return ResponseEntity.status(500).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("notas-examen.xlsx").build());

        return new ResponseEntity<>(archivo, headers, HttpStatus.OK);
    }
}
