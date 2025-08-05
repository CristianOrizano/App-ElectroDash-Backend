package com.gestionventas.repository;

import com.gestionventas.domain.Categoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
public class CategoriaRepositoryTests {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Debe guardar y recuperar una categoría")
    void testGuardarYCargarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electrodomésticos");
        categoria.setNimagen("asdasdd");
        categoria.setState(true);

        Categoria guardada = categoriaRepository.save(categoria);

        assertNotNull(guardada.getId());

        Optional<Categoria> encontrada = categoriaRepository.findById(guardada.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Electrodomésticos", encontrada.get().getNombre());
    }

}
