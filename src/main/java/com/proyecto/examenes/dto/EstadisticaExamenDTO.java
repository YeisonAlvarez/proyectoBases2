package com.proyecto.examenes.dto;

import lombok.Data;

@Data
public class EstadisticaExamenDTO {
    private Long idExamen;
    private Integer totalPresentados;
    private Double promedio;
    private Double maximo;
    private Double minimo;
}
