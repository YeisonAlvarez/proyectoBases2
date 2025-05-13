package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class PlanEstudio implements Serializable {
    private Long id;
    private Long idCurso;
    private String titulo;
    private String descripcion;
}
