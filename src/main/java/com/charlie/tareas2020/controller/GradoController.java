package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.exception.TutorNotFoundException;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.service.GradoService;
import com.charlie.tareas2020.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/grado")

public class GradoController {
    private GradoService gradoService;

    @Autowired
    GradoController(GradoService gradoService){
        this.gradoService = gradoService;
    }

    @GetMapping(value = "/todos")
    public ModelAndView gradoIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Grados grados = gradoService.getGrados();
        modelAndView.addObject("grados", grados);
        modelAndView.setViewName("grado-todos");
        return modelAndView;
    }

    @GetMapping(value = "/{id}/alumnos")
    public ModelAndView gradoAlumnosIndex(@PathVariable(name = "id") String id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Grado> optionalGrado = gradoService.findById(Long.parseLong(id));
        if(optionalGrado.isPresent()){
            modelAndView.addObject("grado", optionalGrado.get());
            modelAndView.setViewName("grado-alumnos");
        }else{
            modelAndView.setViewName("grado-todos");
        }
        return modelAndView;
    }

    @GetMapping(value = "/nuevo")
    public ModelAndView gradoForm(){
        ModelAndView modelAndView = new ModelAndView();
        Grado grado = new Grado();
        modelAndView.addObject("grado", grado);
        modelAndView.setViewName("grado-nuevo");
        return modelAndView;
    }

    @PostMapping(value = "/nuevo")
    public ModelAndView gradoSaved(@Valid Grado grado, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("grado-nuevo");
        } else {
            gradoService.save(grado);
            modelAndView.setViewName("redirect:/grado/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/borrar")
    public ModelAndView gradoDelete(@Valid Grado grado, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        gradoService.deleteById(grado.getId());
        modelAndView.setViewName("redirect:/grado/todos");
        return modelAndView;
    }

    @GetMapping(value = "/modificar/{id}")
    public ModelAndView gradoModify(@PathVariable(name = "id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Grado> optionalGrado = gradoService.findById(id);
        if(optionalGrado.isPresent()){
            modelAndView.addObject("grado", optionalGrado.get());
            modelAndView.setViewName("grado-modificar");
        } else {
            modelAndView.setViewName("redirect:/grado/todos");
        }
        return modelAndView;
    }

    @PostMapping(value="/modificar/{id}")
    public ModelAndView gradoModifyPost(@Valid Grado grado, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(!bindingResult.hasErrors()){
            gradoService.save(grado);
            modelAndView.setViewName("redirect:/grado/todos");
        } else {
            modelAndView.setViewName("grado-modificar");
        }
        return modelAndView;
    }
}
