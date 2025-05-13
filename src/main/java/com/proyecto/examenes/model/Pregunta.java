package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Pregunta implements Serializable {
    private Long id;
    private String texto;
    private String tipo; // SELECCION_UNICA, SELECCION_MULTIPLE, etc.
    private Double porcentaje;
    private String dificultad; // FACIL, MEDIA, DIFICIL
    private Integer tiempoLimite; // en minutos, puede ser null
    private String esPublica; // 'S' o 'N'
    private Long idTema;
    private Long idProfesor;
}
