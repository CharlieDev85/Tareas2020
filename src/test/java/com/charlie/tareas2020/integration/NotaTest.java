package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.service.NotaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class NotaTest {
    @Autowired
    NotaService notaService;
    @Autowired
    private MockMvc mockMvc;

    private final static String PROFESOR_TAREA_NOTAS = "/profesor/tarea/notas";

    @Test
    public void shouldDisplayNotasForm() throws Exception{
        mockMvc.perform(get(PROFESOR_TAREA_NOTAS).param("tarea.id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-tarea-notas"))
                .andExpect(model().attributeExists("tarea"))
                .andExpect(model().attributeExists("notas"))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldSaveNota() throws Exception {
        mockMvc.perform(post(PROFESOR_TAREA_NOTAS)
                .param("id", "")
                .param("alumno", "1")
                .param("tarea", "1")
                .param("punteoAsignado", "9"))
                .andExpect(status().isOk())
                .andExpect(view().name("profesor-tarea-notas"))
                .andExpect(model().attributeExists("tarea"))
                .andExpect(model().attributeExists("notas"))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldNotSaveNotaBecauseItIsHigherThanMax() throws Exception {
        mockMvc.perform(post(PROFESOR_TAREA_NOTAS)
                .param("id", "")
                .param("alumno", "1")
                .param("tarea", "1")
                .param("punteoAsignado", "125"))
                .andExpect(status().isOk())
                .andExpect(view().name("profesor-tarea-notas"))
                .andExpect(model().attributeExists("tarea"))
                .andExpect(model().attributeExists("notas"))
                .andExpect(model().attributeHasFieldErrors("nota", "punteoAsignado"))
                .andExpect(model().hasErrors())
        ;
    }

}
