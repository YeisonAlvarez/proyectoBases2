package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Inscripcion implements Serializable {
    private Long idGrupo;
    private Long idEstudiante;
}
