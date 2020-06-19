package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.controller.TutorController;
import com.charlie.tareas2020.model.Alumno;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorTest {
    @Autowired
    private TutorController tutorController;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private MockMvc mockMvc;

    private final static String TUTOR_DETALLES = "/tutor/detalles/{id}";
    private final static String TUTOR_MODIFICAR = "/tutor/modificar/{id}";
    private final static String INDEX = "/tutor/todos";
    private final static String TUTOR_NUEVO = "/tutor/nuevo";
    private final static String TUTOR_ALUMNO_NUEVO = "/tutor/{id}/alumno/nuevo";


    @Test
    public void contextLoads() {
        assertThat(tutorController).isNotNull();
    }

    @Test
    public void tutorTodos() throws Exception{
        mockMvc.perform(get(INDEX))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("tutor-todos"))
                .andExpect(model().attributeExists("tutores"))
                .andExpect(model().attribute("tutores", hasProperty("tutores")))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldDisplayInscripcionForm() throws Exception{
        mockMvc.perform(get(TUTOR_NUEVO))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("tutor-nuevo"))
                .andExpect(model().attributeExists("alumno"))
                .andExpect(model().attributeExists("grados"))
                .andExpect(model().hasNoErrors())
        ;
    }

    //This event should be handled by the Alumno Controller,
    //so more than 1 Alumno can be added.
    @Test
    public void shouldSaveTutorAndAlunno() throws Exception{
        mockMvc.perform(post(TUTOR_NUEVO)
                .param("alumno.id", "")
                .param("alumno.nombre", "Charlie Marroq")
                .param("alumno.grado.id", "1")
                .param("alumno.tutor.id", "")
                .param("alumno.tutor.nombre", "Elmerito Tutor"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + INDEX))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldDisplayTutorDetails() throws Exception{
        mockMvc.perform(get(TUTOR_DETALLES, 1))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("tutor-detalles"))
                .andExpect(model().attributeExists("tutor"))
                .andExpect(model().attribute("tutor",hasProperty("nombre", is("Tutor Azo"))))
                .andExpect(model().attribute("tutor", hasProperty("alumnos")))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldDisplayModifyTutorForm() throws Exception{
        mockMvc.perform(get(TUTOR_MODIFICAR, 1))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("tutor-modificar"))
                .andExpect(model().attributeExists("tutor"))
                .andExpect(model().attribute("tutor",hasProperty("nombre", is("Tutor Azo"))))
                .andExpect(model().attribute("tutor", hasProperty("alumnos")))
                .andExpect(model().hasNoErrors())
        ;
    }

    //TODO
    /*Testig for update Alumno*/

    @Test
    public void shouldUpdateTutor() throws Exception{
        mockMvc.perform(post(TUTOR_MODIFICAR, 1)
                .param("id", "1")
                .param("nombre", "Tutor Azombroso"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + INDEX))
                .andExpect(model().hasNoErrors())
        ;
        Tutor tutor = tutorService.findById(1l).get();
        assertThat(tutor.getNombre()).isNotEqualTo("Tutor Azo");
        assertThat(tutor.getNombre()).isEqualTo("Tutor Azombroso");
        assertThat(tutor.getAlumnos().size()).isEqualTo(1);
        assertThat(tutor.getAlumnos().get(0).getNombre()).isEqualTo("fulanito deTal");
    }


    @Test
    public void shouldDisplayTutorAlumnoNuevoForm() throws Exception{
        mockMvc.perform(get(TUTOR_ALUMNO_NUEVO, "1"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("tutor-alumno-nuevo"))
                .andExpect(model().attributeExists("tutor"))
                .andExpect(model().attributeExists("alumno"))
                .andExpect(model().attributeExists("grados"))
                .andExpect(model().attribute("tutor",hasProperty("nombre", is("Tutor Azo"))))
                .andExpect(model().attribute("tutor", hasProperty("alumnos")))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    public void shouldSaveNewTutorAlumno() throws Exception{
        Tutor tutor = tutorService.findById(1L).get();
        Alumno alumno = new Alumno();
        alumno.setTutor(tutor);
        mockMvc.perform(post(TUTOR_ALUMNO_NUEVO, "1")
                .param("id", "")
                .param("nombre", "Charlie Marroq")
                .param("grado", "1")
                .param("tutor", alumno.getTutor().getId().toString()))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tutor/detalles/" + tutor.getId().toString()))
                .andExpect(model().hasNoErrors())
        ;
        Tutor tutorModified = tutorService.findById(1L).get();
        assertThat(tutorModified.getAlumnos().size()).isEqualTo(2);
    }

    //TODO
    /*Testing for Delete Tutor with their Alumnos
    * Testing for Delete 1 Alumno of specific Tutor
    * */

}
