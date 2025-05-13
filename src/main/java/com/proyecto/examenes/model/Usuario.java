package com.proyecto.examenes.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Usuario implements Serializable {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol; // 'ESTUDIANTE' o 'PROFESOR'
}
