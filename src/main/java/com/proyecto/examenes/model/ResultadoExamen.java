package com.proyecto.examenes.model;

import lombok.Data;

@Data
public class ResultadoExamen {
    private String nombreExamen;
    private int respuestasCorrectas;
    private int respuestasIncorrectas;
    private double calificacion;
}
