package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class HorarioClase implements Serializable {
    private Long id;
    private Long idGrupo;
    private Long idProfesor;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String aula;
}
