package com.gestionventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionventas.dto.producto.ProductoDto;
import com.gestionventas.service.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setDescripcion("Mouse");
        productoDto.setPrecio(50.0);
        productoDto.setStock(10);
        productoDto.setDescuento(5);
    }

    @Test
    void debeRetornarListaDeProductos() throws Exception {
        when(productoService.findAll()).thenReturn(List.of(productoDto));

        mockMvc.perform(get("/api/producto"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Mouse"));
    }
    @Test
    void testGetProductoById() throws Exception {
        when(productoService.findById(1L)).thenReturn(productoDto);

        mockMvc.perform(get("/api/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
