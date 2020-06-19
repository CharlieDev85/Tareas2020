package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Profesor;
import com.charlie.tareas2020.service.BimestreService;
import com.charlie.tareas2020.service.MateriaService;
import com.charlie.tareas2020.service.ProfesorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfesorTareaTest {

    @Autowired
    ProfesorService profesorService;
    @Autowired
    BimestreService bimestreService;
    @Autowired
    MateriaService materiaService;
    @Autowired
    private MockMvc mockMvc;

    private final static String PROFESOR_TAREA_PASO_1 = "/profesor/tarea/paso1";
    private final static String PROFESOR_TAREA_PASO_2 = "/profesor/tarea/paso2";
    private final static String PROFESOR_TAREA_PASO_3 = "/profesor/tarea/paso3";

    @Test
    public void paso1Get() throws Exception{
        mockMvc.perform(get(PROFESOR_TAREA_PASO_1))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-tarea-paso1"))
                .andExpect(model().attributeExists("profesores"))
                .andExpect(model().attribute("profesores", hasProperty("profesores")))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void paso2Get() throws Exception{
        Profesor profesor = profesorService.findById(1L).get();

        mockMvc.perform(get(PROFESOR_TAREA_PASO_2)
                .param("profesor.id", profesor.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-tarea-paso2"))
                .andExpect(model().attributeExists("profesor"))
                .andExpect(model().attribute("profesor", hasProperty("materias")))
                .andExpect(model().attributeExists("bimestres"))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void paso3Get() throws Exception{
        Materia materia = materiaService.findById(1L).get();
        mockMvc.perform(get(PROFESOR_TAREA_PASO_3)
            .param("materia.id", materia.getId().toString())
            .param("bimestre.id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-tarea-paso3"))
                .andExpect(model().attributeExists("tareas"))
                .andExpect(model().attributeExists("emptyTarea"))
                .andExpect(model().attributeExists("tiposTarea"))
                .andExpect(model().attribute("tareas", hasSize(1)))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void paso3Post() throws Exception{
        mockMvc.perform(post(PROFESOR_TAREA_PASO_3)
                .param("tipoTarea", "2")
                .param("fechaDeEntrega", "2020-06-30")
                .param("tema", "Factorizacion trinomio cuadrado perfecto")
                .param("instrucciones", "Factoriza las siguientes expresiones")
                .param("punteoMaximo", "15")
                .param("materia", "1")
                .param("bimestre", "1"))
                    .andExpect(status().isFound())
                    .andExpect(view().name("redirect:/profesor/todos"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors());
    }

}
