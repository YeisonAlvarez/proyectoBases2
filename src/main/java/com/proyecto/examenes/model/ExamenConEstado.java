package com.proyecto.examenes.model;

import lombok.Data;

@Data
public class ExamenConEstado {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean presentado;  // true si el estudiante ya presentó este examen
}
