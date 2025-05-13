package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Grupo implements Serializable {
    private Long id;
    private Long idCurso;
    private String nombre;
}
