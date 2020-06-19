package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.model.Nota;
import com.charlie.tareas2020.model.Tarea;
import com.charlie.tareas2020.service.GradoService;
import com.charlie.tareas2020.service.NotaService;
import com.charlie.tareas2020.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profesor/tarea")

public class NotaController {
    private NotaService notaService;
    private TareaService tareaService;

    @Autowired
    NotaController(NotaService notaService, TareaService tareaService){
        this.notaService = notaService;
        this.tareaService = tareaService;
    }

    @GetMapping(value = "/notas")
    public ModelAndView notasGet(@RequestParam("tarea.id") String tareaId)  {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Tarea> optionalTarea = tareaService.findById(Long.valueOf(tareaId));

        if(optionalTarea.isPresent()){
            Tarea tarea = optionalTarea.get();
            List<Nota> notas = notaService.findByTarea(tarea);
            modelAndView.addObject("tarea", tarea);
            modelAndView.addObject("notas", notas);
            modelAndView.setViewName("profesor-tarea-notas");
        } else {
            modelAndView.setViewName("redirect:/profesor/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/notas")
    public ModelAndView notasPost(@Valid Nota nota, BindingResult bindingResult){
        ModelAndView modelAndView;
        if(!bindingResult.hasErrors()){
            notaService.save(nota);
        }
        modelAndView = this.notasGet(nota.getTarea().getId().toString());
        return modelAndView;
    }

}
