package com.gestionventas.service;

import com.gestionventas.domain.Categoria;
import com.gestionventas.dto.categoria.CategoriaDto;
import com.gestionventas.dto.categoria.CategoriaSaveDto;
import com.gestionventas.dto.categoria.mapper.CategoriaMapper;
import com.gestionventas.repository.CategoriaRepository;
import com.gestionventas.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CategoriaMapper categoriaMapper;
    @InjectMocks
    private CategoriaServiceImpl categoriaService;
    private CategoriaSaveDto categoriaSaveDto;
    private CategoriaDto categoriaDto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoriaSaveDto = new CategoriaSaveDto();
        categoriaSaveDto.setNombre("Electrónica");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");
        categoria.setState(true);

        categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("Electrónica");
    }

    @DisplayName("Test para guardar un categoria")
    @Test
    void testSaveCategoria() {
        // Definir comportamiento del mock
        when(categoriaMapper.toEntity(categoriaSaveDto)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toDto(categoria)).thenReturn(categoriaDto);

        // Llamar al método de servicio
        CategoriaDto response = categoriaService.create(categoriaSaveDto);

        // Verificar resultados Assert
        assertNotNull(response);
        assertEquals("Electrónica", response.getNombre());
        // Verificar - haya sido llamado exactamente una vez con el objeto categoria como argumento
        verify(categoriaRepository).save(categoria);
    }

    @DisplayName("Test para listar categorías activas")
    @Test
    void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        when(categoriaMapper.toDto(categoria)).thenReturn(categoriaDto);

        List<CategoriaDto> resultado = categoriaService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    @DisplayName("Test findById - cuando existe la categoría")
    void testFindByIdSuccess() {
        Long id = 1L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toDto(categoria)).thenReturn(categoriaDto);

        CategoriaDto resultado = categoriaService.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Electrónica", resultado.getNombre());
    }

    @Test
    @DisplayName("Test update - cuando la categoría existe")
    void testUpdateCategoriaSuccess() {
        Long id = 1L;

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.updateEntity(categoriaSaveDto, categoria)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toDto(categoria)).thenReturn(categoriaDto);

        CategoriaDto resultado = categoriaService.update(id, categoriaSaveDto);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(categoriaMapper).updateEntity(categoriaSaveDto, categoria);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    @DisplayName("Test disable - cuando la categoría existe")
    void testDisableCategoriaSuccess() {
        Long id = 1L;
        categoria.setState(true); // Estado actual

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toDto(categoria)).thenReturn(categoriaDto);

        CategoriaDto resultado = categoriaService.disable(id);

        assertNotNull(resultado);
        assertFalse(categoria.getState()); // Se invirtió

        verify(categoriaRepository).save(categoria);
    }


}
