package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ExamenPregunta implements Serializable {
    private Long idExamen;
    private Long idPregunta;
    private Double porcentaje;
}
