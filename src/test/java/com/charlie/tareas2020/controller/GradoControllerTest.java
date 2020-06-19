package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.service.GradoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(GradoController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class GradoControllerTest {
    private final static String INDEX = "/grado/todos";
    private final static String NUEVO = "/grado/nuevo";
    private final static String MODIFICAR = "/grado/modificar/{id}";
    private final static String BORRAR  = "/grado/borrar";

    @Autowired private MockMvc mvc;
    @Autowired private WebApplicationContext wac;
    @MockBean private GradoService gradoService;

    private Grado primero;
    private Grado segundo;
    private Grado tercero;

    @BeforeAll
    public void setup() {
        //this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        primero = new Grado(1L, "1ero", "Primero Basico",null);
        segundo = new Grado(2L, "2do", "Segundo Basico",null);
        tercero = new Grado(3L, "3ro", "Tercero Basico",null);

    }

    @Test
    public void shouldFindAll() throws Exception {
        ArrayList<Grado> gradosList = new ArrayList();
        gradosList.add(primero);
        gradosList.add(segundo);
        Grados grados = new Grados(gradosList);
        when(gradoService.getGrados()).thenReturn(grados);
        this.mvc.perform(MockMvcRequestBuilders
                .get(INDEX))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("grado-todos"))
                .andExpect(model().attributeExists("grados"))
                .andExpect(model().hasNoErrors())
                ;
        verify(gradoService, times(1)).getGrados();
        verifyNoMoreInteractions(gradoService);
    }
    @Test
    void shouldDisplayNewForm() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get(NUEVO))
                .andExpect(status().isOk())
                .andExpect(view().name("grado-nuevo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("grado"))
        ;
    }
    @Test
    void shouldNotSave() throws Exception{
        Grado grado = new Grado();
        grado.setId(6L);
        grado.setNombre("");
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                .param("id", grado.getId().toString())
                .param("nombre", grado.getNombre()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("grado-nuevo"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeHasErrors("grado"))
                    .andExpect(model().attributeHasFieldErrors("grado"))
                    .andExpect(model().attribute("grado", hasProperty("nombre", is(""))))
                    .andExpect(model().attribute("grado", hasProperty("id", is(6L))))
                    .andExpect(model().hasErrors())
        ;
        verify(gradoService,times(0)).save(grado);
    }
    @Test
    void shouldSave() throws Exception{
        Grado grado = new Grado();
        grado.setNombre("Sexto Primaria");
        grado.setNombreCorto("6to");
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                //.param("id", grado.getId().toString())
                .param("nombre", grado.getNombre())
                .param("nombreCorto", grado.getNombreCorto()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/grado/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
        verify(gradoService,times(1)).save(grado);
    }
    @Test
    void shouldDisplayUpdateForm() throws Exception {
        when(gradoService.findById(3L)).thenReturn(Optional.of(tercero));
        this.mvc.perform(MockMvcRequestBuilders
                .get(MODIFICAR, 3))
                    .andExpect(status().isOk())
                    .andExpect(view().name("grado-modificar"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("grado"))
                    .andExpect(model().attributeHasNoErrors("grado"))
                    .andExpect(model().hasNoErrors())
        ;
    }
    @Test
    void shouldNotUpdate() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.post(MODIFICAR, 3)
            .param("id", tercero.getId().toString())
            .param("nombreCorto", tercero.getNombreCorto())
            .param("nombre", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("grado-modificar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
        ;
        verify(gradoService,times(0)).save(tercero);
    }
    @Test
    void shouldNotUpdateTooLong() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.post(MODIFICAR, 3)
                .param("id", tercero.getId().toString())
                .param("nombreCorto", "this is really a no short name")
                .param("nombre", "nuevo Nombre"))
                .andExpect(status().isOk())
                .andExpect(view().name("grado-modificar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
        ;
        verify(gradoService,times(0)).save(tercero);
    }
    @Test
    void shouldUpdate() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post(NUEVO)
            .param("id", "")
            .param("nombreCorto","Conta")
            .param("nombre","Contabilidad Financiera"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/grado/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
        verify(gradoService,times(1)).save(any(Grado.class));
    }
    @Test
    void shouldNotDisplayUpdateForm() throws Exception{
        when(gradoService.findById(1L)).thenReturn(Optional.empty());
        this.mvc.perform(MockMvcRequestBuilders
                .get(MODIFICAR, 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/grado/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                ;
        verify(gradoService,times(0)).save(any(Grado.class));
    }
    @Test
    void shouldDelete() throws Exception {
        Grado grado = new Grado();
        grado.setId(1L);
        grado.setNombre("1ero. Basico");

        this.mvc.perform(MockMvcRequestBuilders
                .post(BORRAR)
                .param("id", grado.getId().toString()))
                        .andExpect(status().isFound())
                        .andExpect(view().name("redirect:/grado/todos"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(model().hasNoErrors())
        ;
        verify(gradoService,times(1)).deleteById(1L);
    }




}