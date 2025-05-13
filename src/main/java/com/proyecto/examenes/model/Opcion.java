package com.proyecto.examenes.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Opcion implements Serializable {
    private Long id;
    private String texto;
    private String esCorrecta; // 'S' o 'N'
    private Long idPregunta;
}
