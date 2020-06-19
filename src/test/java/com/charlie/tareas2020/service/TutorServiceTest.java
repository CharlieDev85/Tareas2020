package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {
    @Mock
    TutorRepository tutorRepository;
    @InjectMocks
    TutorService tutorService;

    @Test
    public void getTutores() {
        //given
        Tutor betty = new Tutor();
        Tutor armando = new Tutor();
        betty.setId(1L);
        betty.setNombre("betty");
        armando.setId(2L);
        armando.setNombre("armando");
        List<Tutor> tutoresList = new ArrayList<>();
        tutoresList.add(betty);
        tutoresList.add(armando);
        //when
        when(tutorRepository.findAll()).thenReturn(tutoresList);
        Tutores tutores = tutorService.getTutores();
        //then
        assertThat(tutores.getTutores().size()).isEqualTo(2);
        assertThat(tutores.getTutores().get(1).getNombre()).isEqualTo("armando");
    }

}