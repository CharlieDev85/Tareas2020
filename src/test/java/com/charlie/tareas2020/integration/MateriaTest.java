package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.controller.TutorController;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.service.MateriaService;
import com.charlie.tareas2020.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class MateriaTest {
    @Autowired
    private MateriaService materiaService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(materiaService).isNotNull();
    }

    //Testing Create
    @Test
    void shouldSave() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/materia/nuevo")
                .param("id", "")
                .param("nombre", "contabilidad basica")
                .param("grado", "2")
                .param("materiaGrado", "conta2")
                .param("profesor", ""))
                    .andExpect(status().isFound())
                    .andExpect(view().name("redirect:/materia/todos"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
        ;
        Materia materia = materiaService.findLastInserted();
        assertThat(materia.getNombre()).isEqualTo("contabilidad basica");
        assertThat(materia.getGrado().getNombre()).isEqualTo("Segundo Basico");
        assertThat(materia.getProfesor()).isNull();
        assertThat(materia.getMateriaGrado()).isEqualTo("conta2");
    }

    //Testing Read
    //duplicated, the same test isn in TestingWebapplicationTests.java
    @Test
    void shouldReadOne() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/materia/modificar/" + 3))
                .andExpect(status().isOk())
                .andExpect(view().name("materia-modificar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("materia"))
                .andExpect(model().attribute("materia", hasProperty("nombre", is("ciencias naturales"))))
                .andExpect(model().attributeExists("grados"))
        ;

    }


    //Testing Update
    //Test is already in TestingApplicationTest.java

    //Testing Delete
    @Test
    void shouldDelete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/materia/borrar")
                .param("id", "2"))
                    .andExpect(status().isFound())
                    .andExpect(view().name("redirect:/materia/todos"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
        ;
        Optional<Materia> optionalMateria = materiaService.findById(2L);
        assertThat(optionalMateria.isPresent()).isFalse();
    }


}
