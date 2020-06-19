package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Grado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GradoRepositoryTest {

    @Autowired GradoRepository gradoRepository;

    @Test
    public void findGradoById(){
        Optional<Grado> primeroBasico = gradoRepository.findById(1L);
        assertTrue(primeroBasico.isPresent());
        assertThat(primeroBasico.get().getNombre()).isEqualTo("1ro. Basico");
    }

    @Test
    public void findAllGrados(){
        List<Grado> grados = gradoRepository.findAll();
        assertThat(grados.size()).isEqualTo(3);
    }
}