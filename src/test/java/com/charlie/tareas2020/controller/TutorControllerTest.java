package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.service.TutorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TutorController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TutorControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private TutorService tutorService;
    private final static String INDEX = "/tutor/todos";
    private final static String NUEVO = "/tutor/nuevo";
    private final static String MODIFICAR = "/tutor/modificar/{id}";
    private final static String BORRAR  = "/tutor/borrar";
    Tutor azo;

    @BeforeAll
    public void setup() {
        //this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        azo = new Tutor(8L, "Charlie", null);

    }

    @Test
    public void shouldFindAll() throws Exception {
        Tutor azo = new Tutor();
        azo.setNombre("Tutor Azzo");
        Tutor cito = new Tutor();
        cito.setNombre("Tuto R. Cito");
        List<Tutor> tutoresList = new ArrayList();
        tutoresList.add(azo);
        tutoresList.add(cito);
        Tutores tutores = new Tutores(tutoresList);
        when(tutorService.getTutores()).thenReturn(tutores);
        this.mvc.perform(MockMvcRequestBuilders
                .get(INDEX))
                    .andExpect(status().isOk())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(view().name("tutor-todos"))
                    .andExpect(model().attributeExists("tutores"))
                    .andExpect(model().hasNoErrors())
                ;


        verify(tutorService, times(1)).getTutores();
        verifyNoMoreInteractions(tutorService);
    }

    @Test
    void shouldDisplayNewForm() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get(NUEVO))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("tutor-nuevo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("tutor"))
        ;
    }

    @Test
    void shouldNotSave() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                .param("id", "")
                .param("nombre", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("tutor-nuevo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
        ;
        verifyNoInteractions(tutorService);
    }
    @Test
    void shouldSave() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                .param("id", azo.getId().toString())
                .param("nombre", azo.getNombre()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/tutor/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
        verify(tutorService,times(1)).save(azo);
    }


    @Test
    void shouldDisplayUpdateForm() throws Exception {
        Tutor azo = new Tutor();
        azo.setId(8L);
        azo.setNombre("Tutor Azo");
        when(tutorService.findById(8L)).thenReturn(Optional.of(azo));
        this.mvc.perform(MockMvcRequestBuilders
                .get(MODIFICAR, 8))
                    .andExpect(status().isOk())
                    .andExpect(view().name("tutor-modificar"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("tutor"))
                    .andExpect(model().attribute("tutor", hasProperty("nombre", is("Tutor Azo"))))
                    .andExpect(model().attributeHasNoErrors("tutor"))
                    .andExpect(model().hasNoErrors())
                ;
    }
    @Test
    void shouldNotDisplayUpdateForm() throws Exception{
        when(tutorService.findById(1L)).thenReturn(Optional.empty());
        this.mvc.perform(MockMvcRequestBuilders
                .get(MODIFICAR, 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/tutor/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    void shouldNotUpdate() throws Exception{
        when(tutorService.findById(8L)).thenReturn(Optional.of(azo));
        this.mvc.perform(MockMvcRequestBuilders
                .post(MODIFICAR, 8)
                .param("id", azo.getId().toString())
                .param("nombre", ""))
                    .andExpect(status().isOk())
                    .andExpect(view().name("tutor-modificar"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().hasErrors())
        ;
        verify(tutorService,times(0)).save(azo);
    }

    @Test
    void shouldUpdate() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders
                .post(MODIFICAR, 8)
                .param("id", "8")
                .param("nombre", "Charlie Azo"))
                    .andExpect(status().isFound())
                    .andExpect(view().name("redirect:/tutor/todos"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
        ;
        verify(tutorService,times(1)).update(any(Tutor.class));
    }


    @Test
    void shouldDelete() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .post(BORRAR)
                .param("id", azo.getId().toString()))
                        .andExpect(status().isFound())
                        .andExpect(view().name("redirect:/tutor/todos"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(model().hasNoErrors())
        ;
        verify(tutorService,times(1)).deleteById(8L);
    }

}