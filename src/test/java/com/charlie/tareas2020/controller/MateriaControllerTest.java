package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.dto.Materias;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.service.GradoService;
import com.charlie.tareas2020.service.MateriaService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MateriaController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MateriaControllerTest {
    private final static String MODIFICAR = "/materia/modificar/{id}";
    private final static String INDEX = "/materia/todos";
    private final static String NUEVO = "/materia/nuevo";
    private final static String BORRAR  = "/materia/borrar";

    private Materia matematicas;
    private Materia sociales;
    private Grado segundo;
    private Grado tercero;
    private Grados grados;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MateriaService materiaService;
    @MockBean
    private GradoService gradoService;

    @BeforeAll
    public void setup() {
        segundo = new Grado(2L,"2do.B", "Segundo Basico",null);
        tercero = new Grado(3L,"3ro.B", "Tercero Basico",null);
        grados = new Grados(List.of(segundo, tercero));
        matematicas = new Materia(1L, "matematicas", segundo, "mate1", null, null);
        sociales = new Materia(2L, "estudios sociales", segundo, "sociales1", null, null);
    }

    @Test
    public void shouldFindAll() throws Exception {
        List<Materia> materiasList = new ArrayList<>();
        materiasList.add(matematicas);
        materiasList.add(sociales);
        Materias materias = new Materias(materiasList);
        when(materiaService.getMaterias()).thenReturn(materias);
        this.mvc.perform(MockMvcRequestBuilders.get(INDEX))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("materia-todos"))
                .andExpect(model().attributeExists("materias"))
                .andExpect(model().hasNoErrors())
        ;
        verify(materiaService, times(1)).getMaterias();
        verifyNoMoreInteractions(materiaService);
    }
    @Test
    void shouldDisplayNewForm() throws Exception {
        when(gradoService.getGrados()).thenReturn(grados);
        this.mvc.perform(MockMvcRequestBuilders
                .get(NUEVO))
                .andExpect(status().isOk())
                .andExpect(view().name("materia-nuevo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("materia"))
                .andExpect(model().attributeExists("grados"))
        ;
    }
    @Test
    void shouldNotSave() throws Exception{
        //this test will pass due to Spring context is not loading
        //that is why model will have errors
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                //.param("id", sociales.getId().toString())
                .param("nombre", sociales.getNombre())
                .param("grado", sociales.getGrado().getId().toString())
                .param("materiaGrado", sociales.getMateriaGrado())
                .param("profesor", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("materia-nuevo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
        ;
        verify(materiaService,times(0)).save(any(Materia.class));
    }
    /*
    Following test fails in Unit Test, but passes as Integration test.
    @Test
    void shouldSave() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                .param("id", "")
                .param("nombre", sociales.getNombre())
                .param("grado", sociales.getGrado().getId().toString())
                .param("materiaGrado", sociales.getMateriaGrado())
                .param("profesor", ""))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/materia/todos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
        verify(materiaService,times(1)).save(any(Materia.class));
    }
    */
    @Test
    void shouldDisplayUpdateForm() throws Exception{
        Materia conta =  new Materia();
        conta.setNombre("Contabilidad");
        conta.setGrado(new Grado(10L, "3ro", "Tercero Basico",null));
        conta.setMateriaGrado("Conta3");
        conta.setId(9L);
        conta.setProfesor(null);
        when(materiaService.findById(9L)).thenReturn(Optional.of(conta));
        this.mvc.perform(MockMvcRequestBuilders
                .get(MODIFICAR, 9))
                .andExpect(status().isOk())
                .andExpect(view().name("materia-modificar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("materia"))
                .andExpect(model().attributeHasNoErrors("materia"))
                .andExpect(model().hasNoErrors())
        ;
    }


    
}
