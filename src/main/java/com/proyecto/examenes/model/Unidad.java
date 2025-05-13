package com.proyecto.examenes.model;

import lombok.Data;

@Data
public class Unidad {
    private Long id;
    private String nombre;
    private Integer numero;
    private Long idPlanEstudio;
}
