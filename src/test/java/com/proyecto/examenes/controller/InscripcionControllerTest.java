package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Inscripcion;
import com.proyecto.examenes.model.Usuario;
import com.proyecto.examenes.repository.InscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class InscripcionControllerTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private InscripcionController inscripcionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inscribir_Success() {
        // Arrange
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdGrupo(1L);
        inscripcion.setIdEstudiante(1L);
        
        when(inscripcionRepository.inscribir(any(Inscripcion.class))).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = inscripcionController.inscribir(inscripcion);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inscripción realizada correctamente", response.getBody());
        verify(inscripcionRepository).inscribir(inscripcion);
    }

    @Test
    void inscribir_Failure() {
        // Arrange
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdGrupo(1L);
        inscripcion.setIdEstudiante(1L);
        
        when(inscripcionRepository.inscribir(any(Inscripcion.class))).thenReturn(false);
        
        // Act
        ResponseEntity<?> response = inscripcionController.inscribir(inscripcion);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al inscribir", response.getBody());
        verify(inscripcionRepository).inscribir(inscripcion);
    }

    @Test
    void listar_Success() {
        // Arrange
        List<Inscripcion> inscripciones = new ArrayList<>();
        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.setIdGrupo(1L);
        inscripcion1.setIdEstudiante(1L);
        
        Inscripcion inscripcion2 = new Inscripcion();
        inscripcion2.setIdGrupo(1L);
        inscripcion2.setIdEstudiante(2L);
        
        inscripciones.add(inscripcion1);
        inscripciones.add(inscripcion2);
        
        when(inscripcionRepository.listarInscripciones()).thenReturn(inscripciones);
        
        // Act
        List<Inscripcion> result = inscripcionController.listar();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(inscripcion1.getIdGrupo(), result.get(0).getIdGrupo());
        assertEquals(inscripcion1.getIdEstudiante(), result.get(0).getIdEstudiante());
        assertEquals(inscripcion2.getIdGrupo(), result.get(1).getIdGrupo());
        assertEquals(inscripcion2.getIdEstudiante(), result.get(1).getIdEstudiante());
        verify(inscripcionRepository).listarInscripciones();
    }

    @Test
    void eliminar_Success() {
        // Arrange
        Long idGrupo = 1L;
        Long idEstudiante = 1L;
        
        when(inscripcionRepository.eliminarInscripcion(idGrupo, idEstudiante)).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = inscripcionController.eliminar(idGrupo, idEstudiante);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inscripción eliminada correctamente", response.getBody());
        verify(inscripcionRepository).eliminarInscripcion(idGrupo, idEstudiante);
    }

    @Test
    void eliminar_Failure() {
        // Arrange
        Long idGrupo = 1L;
        Long idEstudiante = 1L;
        
        when(inscripcionRepository.eliminarInscripcion(idGrupo, idEstudiante)).thenReturn(false);
        
        // Act
        ResponseEntity<?> response = inscripcionController.eliminar(idGrupo, idEstudiante);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al eliminar la inscripción", response.getBody());
        verify(inscripcionRepository).eliminarInscripcion(idGrupo, idEstudiante);
    }

    @Test
    void listarPorGrupo_Success() {
        // Arrange
        Long idGrupo = 1L;
        List<Inscripcion> inscripciones = new ArrayList<>();
        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.setIdGrupo(idGrupo);
        inscripcion1.setIdEstudiante(1L);
        
        Inscripcion inscripcion2 = new Inscripcion();
        inscripcion2.setIdGrupo(idGrupo);
        inscripcion2.setIdEstudiante(2L);
        
        inscripciones.add(inscripcion1);
        inscripciones.add(inscripcion2);
        
        when(inscripcionRepository.listarInscripcionesPorGrupo(idGrupo)).thenReturn(inscripciones);
        
        // Act
        List<Inscripcion> result = inscripcionController.listarPorGrupo(idGrupo);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(idGrupo, result.get(0).getIdGrupo());
        assertEquals(1L, result.get(0).getIdEstudiante());
        assertEquals(idGrupo, result.get(1).getIdGrupo());
        assertEquals(2L, result.get(1).getIdEstudiante());
        verify(inscripcionRepository).listarInscripcionesPorGrupo(idGrupo);
    }

    @Test
    void listarPorEstudiante_Success() {
        // Arrange
        Long idEstudiante = 1L;
        List<Inscripcion> inscripciones = new ArrayList<>();
        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.setIdGrupo(1L);
        inscripcion1.setIdEstudiante(idEstudiante);
        
        Inscripcion inscripcion2 = new Inscripcion();
        inscripcion2.setIdGrupo(2L);
        inscripcion2.setIdEstudiante(idEstudiante);
        
        inscripciones.add(inscripcion1);
        inscripciones.add(inscripcion2);
        
        when(inscripcionRepository.listarInscripcionesPorEstudiante(idEstudiante)).thenReturn(inscripciones);
        
        // Act
        List<Inscripcion> result = inscripcionController.listarPorEstudiante(idEstudiante);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getIdGrupo());
        assertEquals(idEstudiante, result.get(0).getIdEstudiante());
        assertEquals(2L, result.get(1).getIdGrupo());
        assertEquals(idEstudiante, result.get(1).getIdEstudiante());
        verify(inscripcionRepository).listarInscripcionesPorEstudiante(idEstudiante);
    }

    @Test
    void listarEstudiantesPorGrupo_Success() {
        // Arrange
        Long idGrupo = 1L;
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Usuario 1");
        usuario1.setCorreo("usuario1@example.com");
        usuario1.setRol("ESTUDIANTE");
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Usuario 2");
        usuario2.setCorreo("usuario2@example.com");
        usuario2.setRol("ESTUDIANTE");
        
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        
        when(inscripcionRepository.listarEstudiantesPorGrupo(idGrupo)).thenReturn(usuarios);
        
        // Act
        List<Usuario> result = inscripcionController.listarEstudiantesPorGrupo(idGrupo);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(usuario1.getId(), result.get(0).getId());
        assertEquals(usuario1.getNombre(), result.get(0).getNombre());
        assertEquals(usuario1.getCorreo(), result.get(0).getCorreo());
        assertEquals(usuario1.getRol(), result.get(0).getRol());
        assertEquals(usuario2.getId(), result.get(1).getId());
        assertEquals(usuario2.getNombre(), result.get(1).getNombre());
        assertEquals(usuario2.getCorreo(), result.get(1).getCorreo());
        assertEquals(usuario2.getRol(), result.get(1).getRol());
        verify(inscripcionRepository).listarEstudiantesPorGrupo(idGrupo);
    }
}