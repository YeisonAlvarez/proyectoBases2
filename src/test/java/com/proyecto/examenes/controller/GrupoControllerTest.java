package com.proyecto.examenes.controller;

import com.proyecto.examenes.model.Grupo;
import com.proyecto.examenes.repository.GrupoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GrupoControllerTest {

    @Mock
    private GrupoRepository grupoRepository;

    @InjectMocks
    private GrupoController grupoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_Success() {
        // Arrange
        Grupo grupo = new Grupo();
        grupo.setIdCurso(1L);
        grupo.setNombre("Test Grupo");
        
        when(grupoRepository.crearGrupo(any(Grupo.class))).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = grupoController.crear(grupo);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grupo creado correctamente", response.getBody());
        verify(grupoRepository).crearGrupo(grupo);
    }

    @Test
    void crear_Failure() {
        // Arrange
        Grupo grupo = new Grupo();
        grupo.setIdCurso(1L);
        grupo.setNombre("Test Grupo");
        
        when(grupoRepository.crearGrupo(any(Grupo.class))).thenReturn(false);
        
        // Act
        ResponseEntity<?> response = grupoController.crear(grupo);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al crear el grupo", response.getBody());
        verify(grupoRepository).crearGrupo(grupo);
    }

    @Test
    void listar_Success() {
        // Arrange
        List<Grupo> grupos = new ArrayList<>();
        Grupo grupo1 = new Grupo();
        grupo1.setId(1L);
        grupo1.setIdCurso(1L);
        grupo1.setNombre("Grupo 1");
        
        Grupo grupo2 = new Grupo();
        grupo2.setId(2L);
        grupo2.setIdCurso(1L);
        grupo2.setNombre("Grupo 2");
        
        grupos.add(grupo1);
        grupos.add(grupo2);
        
        when(grupoRepository.listarGrupos()).thenReturn(grupos);
        
        // Act
        List<Grupo> result = grupoController.listar();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(grupo1.getId(), result.get(0).getId());
        assertEquals(grupo1.getNombre(), result.get(0).getNombre());
        assertEquals(grupo2.getId(), result.get(1).getId());
        assertEquals(grupo2.getNombre(), result.get(1).getNombre());
        verify(grupoRepository).listarGrupos();
    }

    @Test
    void buscarPorId_Found() {
        // Arrange
        Long id = 1L;
        Grupo grupo = new Grupo();
        grupo.setId(id);
        grupo.setIdCurso(1L);
        grupo.setNombre("Grupo 1");
        
        when(grupoRepository.buscarPorId(id)).thenReturn(Optional.of(grupo));
        
        // Act
        ResponseEntity<?> response = grupoController.buscarPorId(id);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grupo, response.getBody());
        verify(grupoRepository).buscarPorId(id);
    }

    @Test
    void buscarPorId_NotFound() {
        // Arrange
        Long id = 1L;
        when(grupoRepository.buscarPorId(id)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<?> response = grupoController.buscarPorId(id);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(grupoRepository).buscarPorId(id);
    }

    @Test
    void actualizar_Success() {
        // Arrange
        Long id = 1L;
        Grupo grupo = new Grupo();
        grupo.setIdCurso(2L);
        grupo.setNombre("Updated Grupo");
        
        when(grupoRepository.actualizarGrupo(anyLong(), any(Grupo.class))).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = grupoController.actualizar(id, grupo);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grupo actualizado correctamente", response.getBody());
        verify(grupoRepository).actualizarGrupo(id, grupo);
    }

    @Test
    void actualizar_Failure() {
        // Arrange
        Long id = 1L;
        Grupo grupo = new Grupo();
        grupo.setIdCurso(2L);
        grupo.setNombre("Updated Grupo");
        
        when(grupoRepository.actualizarGrupo(anyLong(), any(Grupo.class))).thenReturn(false);
        
        // Act
        ResponseEntity<?> response = grupoController.actualizar(id, grupo);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al actualizar el grupo", response.getBody());
        verify(grupoRepository).actualizarGrupo(id, grupo);
    }

    @Test
    void eliminar_Success() {
        // Arrange
        Long id = 1L;
        when(grupoRepository.eliminarGrupo(id)).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = grupoController.eliminar(id);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grupo eliminado correctamente", response.getBody());
        verify(grupoRepository).eliminarGrupo(id);
    }

    @Test
    void eliminar_Failure() {
        // Arrange
        Long id = 1L;
        when(grupoRepository.eliminarGrupo(id)).thenReturn(false);
        
        // Act
        ResponseEntity<?> response = grupoController.eliminar(id);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al eliminar el grupo", response.getBody());
        verify(grupoRepository).eliminarGrupo(id);
    }

    @Test
    void listarPorCurso_Success() {
        // Arrange
        Long idCurso = 1L;
        List<Grupo> grupos = new ArrayList<>();
        Grupo grupo1 = new Grupo();
        grupo1.setId(1L);
        grupo1.setIdCurso(idCurso);
        grupo1.setNombre("Grupo 1");
        
        Grupo grupo2 = new Grupo();
        grupo2.setId(2L);
        grupo2.setIdCurso(idCurso);
        grupo2.setNombre("Grupo 2");
        
        grupos.add(grupo1);
        grupos.add(grupo2);
        
        when(grupoRepository.listarGruposPorCurso(idCurso)).thenReturn(grupos);
        
        // Act
        List<Grupo> result = grupoController.listarPorCurso(idCurso);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(grupo1.getId(), result.get(0).getId());
        assertEquals(grupo1.getNombre(), result.get(0).getNombre());
        assertEquals(grupo2.getId(), result.get(1).getId());
        assertEquals(grupo2.getNombre(), result.get(1).getNombre());
        verify(grupoRepository).listarGruposPorCurso(idCurso);
    }
}