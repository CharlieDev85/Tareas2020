package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.controller.GradoController;
import com.charlie.tareas2020.service.AlumnoService;
import com.charlie.tareas2020.service.GradoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class GradoTest {
    @Autowired
    private GradoController gradoController;
    @Autowired
    private GradoService gradoService;
    @Autowired
    private MockMvc mockMvc;

    private final static String GRADO_ALUMNOS = "/grado/{id}/alumnos";

    @Test
    public void tutorTodos() throws Exception{
        mockMvc.perform(get(GRADO_ALUMNOS, "1"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("grado-alumno-todos"))
                .andExpect(model().attributeExists("grado"))
                .andExpect(model().attribute("grado", hasProperty("alumnos")))
                .andExpect(model().hasNoErrors())
        ;
    }
}
