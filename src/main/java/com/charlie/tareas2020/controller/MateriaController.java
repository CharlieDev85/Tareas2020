package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.dto.Materias;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.service.GradoService;
import com.charlie.tareas2020.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/materia")
public class MateriaController {

    private MateriaService materiaService;
    private GradoService gradoService;

    @Autowired
    MateriaController(MateriaService materiaService, GradoService gradoService){
        this.materiaService = materiaService;
        this.gradoService = gradoService;
    }

    @GetMapping(value = "/todos")
    public ModelAndView materiaIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Materias materias = materiaService.getMaterias();
        modelAndView.addObject("materias", materias);
        modelAndView.setViewName("materia-todos");
        return modelAndView;
    }

    @GetMapping(value = "/nuevo")
    public ModelAndView materiaForm(){
        ModelAndView modelAndView = new ModelAndView();
        Materia materia = new Materia();
        Grados grados = gradoService.getGrados();
        modelAndView.addObject("materia", materia);
        modelAndView.addObject("grados", grados);
        modelAndView.setViewName("materia-nuevo");
        return modelAndView;
    }

    @PostMapping(value = "/nuevo")
    public ModelAndView materiaSaved(@Valid Materia materia, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("materia-nuevo");
        } else {
            Materia materiaSaved = materiaService.save(materia);
            modelAndView.setViewName("redirect:/materia/todos");
            modelAndView.addObject("materiaSaved", materiaSaved);
        }
        return modelAndView;
    }

    @PostMapping(value = "/borrar")
    public ModelAndView materiaDelete(@RequestParam("id") String id){
        ModelAndView modelAndView = new ModelAndView();
        materiaService.deleteById(Long.parseLong(id));
        modelAndView.setViewName("redirect:/materia/todos");
        return modelAndView;
    }

    @GetMapping(value = "/modificar/{id}")
    public ModelAndView materiaModifyGet(@PathVariable(name = "id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Materia> optionalMateria = materiaService.findById(id);
        if(optionalMateria.isPresent()){
            Grados grados = gradoService.getGrados();
            modelAndView.addObject("grados", grados);
            modelAndView.addObject("materia", optionalMateria.get());
            modelAndView.setViewName("materia-modificar");
        } else {
            modelAndView.setViewName("redirect:/materia/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/modificar/{id}")
    public ModelAndView materiaModifyPost(@Valid Materia materia, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("this is the valid materia: " + materia);
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("materia-modificar");
        } else {
            materiaService.save(materia);
            modelAndView.setViewName("redirect:/materia/todos");
        }
        return modelAndView;
    }

}
