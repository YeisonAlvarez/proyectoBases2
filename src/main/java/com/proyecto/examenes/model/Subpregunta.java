package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Subpregunta implements Serializable {
    private Long id;
    private String texto;
    private Double porcentaje;
    private Long idPreguntaPadre;
}
