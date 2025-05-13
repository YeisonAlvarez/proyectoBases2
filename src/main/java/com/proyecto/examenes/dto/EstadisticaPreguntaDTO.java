package com.proyecto.examenes.dto;

import lombok.Data;

@Data
public class EstadisticaPreguntaDTO {
    private Long idPregunta;
    private String textoPregunta;
    private Integer totalRespuestas;
    private Integer correctas;
    private Integer incorrectas;
    private Double porcentajeAcierto;
}
