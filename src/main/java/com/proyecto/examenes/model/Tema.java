package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Tema implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long idCurso;
}
