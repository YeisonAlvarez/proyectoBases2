package com.proyecto.examenes.dto;

import lombok.Data;
import java.util.List;

@Data
public class RespuestaEstudianteDTO {
    private Long idEstudiante;
    private Long idExamen;
    private List<RespuestaIndividualDTO> respuestas;
    private String ipOrigen;
}
