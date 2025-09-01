package com.gestionventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionventas.domain.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CategoriaIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarCategorias() throws Exception {
        mockMvc.perform(get("/api/categoria"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // 👈 Esto imprime el response completo en consola
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void testBuscarCategoriaPorId() throws Exception {
        long idExistente = 1L;

        mockMvc.perform(get("/api/categoria/{id}", idExistente))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(idExistente))
                .andExpect(jsonPath("$.nombre").isNotEmpty());
    }

    /*@Test
    void testCrearCategoria() throws Exception {
        // Crear objeto real (entidad o DTO)
        Categoria categoria = new Categoria();
        categoria.setNombre("Tecnología");

        // Convertir a JSON
        String json = objectMapper.writeValueAsString(categoria);

        // Ejecutar POST simulando un cliente real y validar respuesta
        mockMvc.perform(post("/api/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tecnología"));
    } */

}
