package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Curso implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
}
