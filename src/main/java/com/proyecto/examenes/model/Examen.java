package com.proyecto.examenes.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Examen implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private int idTema;
    private int totalPreguntasBanco;
    private int totalPreguntasExamen;
    private int duracionMinutos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long idProfesor;
}
