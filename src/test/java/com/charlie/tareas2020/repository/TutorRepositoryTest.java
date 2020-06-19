package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Tutor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
public class TutorRepositoryTest {
    @Autowired
    private TutorRepository tutorRepository;

    private static Tutor carlos, enrique, tutor, robert;

    @BeforeAll
    //@TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public static void init(){
        tutor = new Tutor();
        tutor.setNombre("profe");
        carlos = new Tutor();
        enrique = new Tutor();
        carlos.setNombre("carlos");
        enrique.setNombre("enrique");
    }

    @Test
    public void saveTutor (){
        Tutor found = tutorRepository.save(tutor);
        assertEquals(found.getNombre(), tutor.getNombre());
        assertNotNull(tutor.getId());
    }

    @Test
    public void saveTutorWithNullName (){
        Tutor nullTutor = new Tutor();
        Tutor saved = tutorRepository.save(nullTutor);
        assertThat(saved).isNotNull();
    }

    @Test
    public void getAll(){
        tutorRepository.save(carlos);
        tutorRepository.save(enrique);
        List<Tutor> tutores = tutorRepository.findAll();
        assertEquals(2, tutores.size());
    }

    @Test
    public void idReturnsSameObject(){
        Tutor juancho = new Tutor();
        juancho.setNombre("juancho");
        Tutor savedJuancho = tutorRepository.save(juancho);
        Optional<Tutor> returnJuanchoOptional = tutorRepository.findById(savedJuancho.getId());
        Tutor returnedJuancho = returnJuanchoOptional.get();
        assertThat(returnedJuancho).isEqualTo(juancho);
    }

    @Test
    public void deleteById(){
        robert = new Tutor();
        robert.setNombre("robert");
        Tutor savedTutor = tutorRepository.save(robert);
        Long id = savedTutor.getId();
        tutorRepository.deleteById(id);
        assertNotNull(id);
    }

    @Test
    public void updateTutor(){
        Tutor charlieM = new Tutor();
        charlieM.setNombre("charly");
        Tutor savedCharlieM = tutorRepository.save(charlieM);
        Tutor returnedCharlie = tutorRepository.findById(savedCharlieM.getId()).get();
        returnedCharlie.setNombre("charlieM");
        Tutor updatedCharlie = tutorRepository.saveAndFlush(returnedCharlie);
        assertEquals(updatedCharlie, returnedCharlie);
    }
}