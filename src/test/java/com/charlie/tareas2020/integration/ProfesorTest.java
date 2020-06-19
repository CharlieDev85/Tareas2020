package com.charlie.tareas2020.integration;

import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Profesor;
import com.charlie.tareas2020.service.MateriaService;
import com.charlie.tareas2020.service.ProfesorService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfesorTest {
    private static Long id;
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private MateriaService materiaService;
    @Autowired
    private MockMvc mockMvc;
    private final static String MODIFICAR = "/profesor/modificar/{id}";
    private final static String INDEX = "/profesor/todos";
    private final static String NUEVO = "/profesor/nuevo";
    private final static String BORRAR  = "/profesor/borrar";

    @Test
    @Order(7)
    public void contextLoads() {
        assertThat(profesorService).isNotNull();
    }

    //CRUD Tests:
    //Testing Create
    //Testing Read One
    @Test
    @Order(4)
    @Transactional
    void shouldCreateAndFindToModify () throws Exception {
        LinkedMultiValueMap<String, String> materias = new LinkedMultiValueMap<>();
        materias.add("materias", "2");
        materias.add("materias", "3");
        mockMvc.perform(MockMvcRequestBuilders
            .post(NUEVO)
                .param("id", "")
                .param("nombre", "Tony Stark")
                .params(materias))
                    .andExpect(status().isFound())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + INDEX))
                    .andExpect(model().hasNoErrors())
        ;
        Profesor profesor = profesorService.findLastInserted();
        assertThat(profesor.getNombre()).isEqualTo("Tony Stark");
        assertThat(profesor.getMaterias()).isNotNull();
        this.id = profesor.getId();
        //////////
        mockMvc.perform(MockMvcRequestBuilders.get(MODIFICAR, this.id))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-modificar"))
                .andExpect(model().attributeExists("profesor"))
                .andExpect(model().attributeExists("materiasWithNoProfesor"))
                .andExpect(model().hasNoErrors())
        ;
        ///////////////////

    }

    //Testing Read
    //Read All
    @Test
    @Order(3)
    @Transactional
    void shouldFindThemAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-todos"))
                .andExpect(model().attributeExists("profesores"))
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    @Order(6)
    void shouldNotCreateProfesorBecauseMateriasWereNotGiven() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post(NUEVO)
                .param("id", "")
                .param("nombre", "Antonio Chispas"))
                    .andExpect(status().isOk())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(view().name("profesor-nuevo"))
                    .andExpect(model().hasErrors())
        ;
    }

    //Read One
    @Test
    @Order(2)
    @Transactional
    @Disabled
    void shouldFindOne() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(MODIFICAR, this.id))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profesor-modificar"))
                .andExpect(model().attributeExists("profesor"))
                .andExpect(model().attributeExists("materiasWithNoProfesor"))
                .andExpect(model().hasNoErrors())
        ;
    }

    //Testing Update
    @Test
    @Transactional
    @Order(1)
    void shouldUpdate() throws Exception{
        Profesor profesor = profesorService.findById(1L).get();
        assertThat(profesor.getNombre()).isEqualTo("Profe Charlie");
        assertThat(profesor.getMaterias().get(0).getNombre()).isEqualTo("matematica");
        mockMvc.perform(MockMvcRequestBuilders.post(MODIFICAR, profesor.getId().toString())
                .param("id", profesor.getId().toString())
                .param("nombre", "Profe Enrique")
                .param("materias", "2"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/profesor/todos"))
        ;
        Profesor profesorModified = profesorService.findById(1L).get();
        Materia materia = profesorModified.getMaterias().get(0);
        assertThat(profesorModified.getNombre()).isEqualTo("Profe Enrique");
        assertThat(materia.getNombre()).isEqualTo("idioma espanol");
    }

    //Testing Delete
    @Test
    @Order(5)
    void shouldDelete() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post(BORRAR)
                .param("id", "1"))
                    .andExpect(status().isFound())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/profesor/todos"))
                    .andExpect(model().hasNoErrors())
        ;
        Optional<Profesor> optionalProfesor = profesorService.findById(1L);
        Optional<Materia> optionalMateria = materiaService.findById(1L);
        assertThat(optionalProfesor.isPresent()).isFalse();
        assertThat(optionalMateria.get().getProfesor()).isNull();
    }

}
