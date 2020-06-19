package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.controller.TutorController;
import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Profesor;
import com.charlie.tareas2020.service.MateriaService;
import com.charlie.tareas2020.service.TutorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;



import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasProperty;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTests {
    @Autowired
    private TutorController tutorController;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private MateriaService materiaService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(tutorController).isNotNull();
    }

    @Test
    public void gettingTutoresFromDb(){
        Tutores tutores = tutorService.getTutores();
        assertThat(tutores).isNotNull();
        assertThat(tutores.getClass()).hasSameClassAs(Tutores.class);
        assertThat(tutores).isInstanceOf(Tutores.class);
        System.out.println(tutores);
    }

    @Test
    public void gettingMateriaToModify() throws Exception{
        this.mockMvc.perform(get("/materia/modificar/2"))
            .andExpect(status().isOk())
            .andExpect(status().is2xxSuccessful())
            .andExpect(view().name("materia-modificar"))
            .andExpect(model().hasNoErrors())
            .andExpect(model().attributeExists("materia"))
            .andExpect(model().attribute("materia", hasProperty("nombre", is("idioma espanol"))))
        ;
    }

    @Test
    public void modifyingMateria() throws Exception {
        Materia materia = materiaService.findById(3L).get();
        assertThat(materia.getNombre()).isEqualTo("ciencias naturales");

        this.mockMvc.perform(post("/materia/modificar/{id}", materia.getId().toString())
            .param("id", materia.getId().toString())
            .param("nombre", "Fisica Fundamental")
            .param("materiaGrado", "FF3")
            .param("profesor", "")
            .param("grado", materia.getGrado().getId().toString()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/materia/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
        Materia materiaModified = materiaService.findById(3L).get();
        assertThat(materiaModified.getNombre()).isEqualTo("Fisica Fundamental");
    }





}
