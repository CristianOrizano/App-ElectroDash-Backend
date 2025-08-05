package com.gestionventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionventas.dto.categoria.CategoriaDto;
import com.gestionventas.dto.categoria.CategoriaSaveDto;
import com.gestionventas.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoriaServiceImpl categoriaService;

    private CategoriaDto categoriaDto;
    private CategoriaSaveDto categoriaSaveDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("Electrónica");
        categoriaDto.setDescripcion("productos electronica");
        categoriaDto.setState(true);

        categoriaSaveDto = new CategoriaSaveDto();
        categoriaSaveDto.setNombre("Electrónica");
        categoriaSaveDto.setDescripcion("productos electronica");
    }

    @Test
    void testCreateCategoria() throws Exception {
        when(categoriaService.create(any())).thenReturn(categoriaDto);

        mockMvc.perform(post("/api/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaSaveDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Electrónica"))
                .andExpect(jsonPath("$.state").value(true));
    }

    @Test
    void testGetAllCategorias() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of(categoriaDto));

        mockMvc.perform(get("/api/categoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetCategoriaById() throws Exception {
        when(categoriaService.findById(1L)).thenReturn(categoriaDto);

        mockMvc.perform(get("/api/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateCategoria() throws Exception {
        when(categoriaService.update(eq(1L), any())).thenReturn(categoriaDto);

        mockMvc.perform(put("/api/categoria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Electrónica"));
    }

    @Test
    void testDisableCategoria() throws Exception {
        categoriaDto.setState(false);
        when(categoriaService.disable(1L)).thenReturn(categoriaDto);

        mockMvc.perform(delete("/api/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(false));
    }


}
