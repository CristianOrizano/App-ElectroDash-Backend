package com.gestionventas.service;


import com.gestionventas.domain.Categoria;
import com.gestionventas.domain.Producto;
import com.gestionventas.dto.producto.ProductoDto;
import com.gestionventas.dto.producto.ProductoSaveDto;
import com.gestionventas.dto.producto.mapper.ProductoMapper;
import com.gestionventas.repository.CategoriaRepository;
import com.gestionventas.repository.ProductoRepository;
import com.gestionventas.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private ProductoMapper productoMapper;
    @InjectMocks
    private ProductoServiceImpl productoService;
    private Producto producto;
    private ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setDescripcion("Mouse");

        productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setDescripcion("Mouse");
    }

    @Test
    void debeRetornarProductoCuandoExisteId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        ProductoDto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Mouse", resultado.getDescripcion());
        verify(productoRepository).findById(1L);
        verify(productoMapper).toDto(producto);
    }

    @Test
    void debeRetornarListaDeProductos() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setDescripcion("Mouse");
        producto.setStock(10);
        producto.setPrecio(50.0);
        producto.setDescuento(5);
        producto.setState(true);

        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setDescripcion("Mouse");
        productoDto.setStock(10);
        productoDto.setPrecio(50.0);
        productoDto.setDescuento(5);

        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        List<ProductoDto> resultado = productoService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Mouse", resultado.get(0).getDescripcion());
        verify(productoRepository).findAll();
        verify(productoMapper).toDto(producto);
    }

    @Test
    void debeCrearProductoCuandoCategoriaExiste() {
        ProductoSaveDto productoSaveDto = new ProductoSaveDto();
        productoSaveDto.setDescripcion("Laptop");
        productoSaveDto.setPrecio(1500.0);
        productoSaveDto.setIdCategoria(10L);

        Categoria categoria = new Categoria();
        categoria.setId(10L);
        categoria.setNombre("Tecnología");

        Producto productoEntidad = new Producto();
        productoEntidad.setId(1L);
        productoEntidad.setDescripcion("Laptop");
        productoEntidad.setIdCategoria(10L);
        productoEntidad.setState(true);

        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setDescripcion("Laptop");

        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoria));
        when(productoMapper.toEntity(productoSaveDto)).thenReturn(productoEntidad);
        when(productoRepository.save(productoEntidad)).thenReturn(productoEntidad);
        when(productoMapper.toDto(productoEntidad)).thenReturn(productoDto);

        ProductoDto resultado = productoService.create(productoSaveDto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getDescripcion());
        verify(categoriaRepository).findById(10L);
        verify(productoRepository).save(productoEntidad);
        verify(productoMapper).toDto(productoEntidad);
    }


}
