package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Materias;
import com.charlie.tareas2020.dto.Profesores;
import com.charlie.tareas2020.model.Profesor;
import com.charlie.tareas2020.service.MateriaService;
import com.charlie.tareas2020.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    private ProfesorService profesorService;
    private MateriaService materiaService;
    @Autowired
    ProfesorController(ProfesorService profesorService, MateriaService materiaService){
        this.profesorService = profesorService;
        this.materiaService = materiaService;
    }

    @GetMapping("/nuevo")
    public ModelAndView profesorForm(){
        ModelAndView modelAndView = new ModelAndView();
        Profesor profesor = new Profesor();
        modelAndView.addObject("profesor", profesor);
        modelAndView.setViewName("profesor-nuevo");
        return modelAndView;
    }
    @PostMapping("/nuevo")
    public ModelAndView profesorSaved(@Valid Profesor profesor, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(!bindingResult.hasErrors()){
            Profesor profesorSaved = profesorService.save(profesor);
            modelAndView.setViewName("redirect:/profesor/todos");
            modelAndView.addObject("profesorSaved", profesorSaved);
        }
        else{
            modelAndView.setViewName("profesor-nuevo");
        }

        return modelAndView;
    }
    @GetMapping("/todos")
    public ModelAndView profesorIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Profesores profesores = profesorService.getProfesores();
        modelAndView.addObject("profesores", profesores);
        modelAndView.setViewName("profesor-todos");
        return modelAndView;
    }

    @GetMapping("/modificar/{id}")
    public ModelAndView profesorModificarGet(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Profesor> optionalProfesor = profesorService.findById(id);
        if(optionalProfesor.isPresent()){
            Materias materiasWithNoProfesor = materiaService.getMateriasWithNoProfesor();
            modelAndView.addObject("materiasWithNoProfesor", materiasWithNoProfesor);
            modelAndView.addObject("profesor", optionalProfesor.get());
            modelAndView.setViewName("profesor-modificar");
        }else{
            modelAndView.setViewName("redirect:/profesor/todos");
        }
        return modelAndView;
    }

    @PostMapping("modificar/{id}")
    public ModelAndView profesorModificarPost(@Valid Profesor profesor, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(!bindingResult.hasErrors()){
            profesorService.save(profesor);
            modelAndView.setViewName("redirect:/profesor/todos");
        }
        else{
            modelAndView.setViewName("profesor-modificar");
        }
        return modelAndView;
    }

    @PostMapping("/borrar")
    public ModelAndView profesorDelete(@RequestParam("id") String id){
        ModelAndView modelAndView = new ModelAndView();
        profesorService.deleteById(Long.parseLong(id));
        modelAndView.setViewName("redirect:/profesor/todos");
        return modelAndView;
    }

}
