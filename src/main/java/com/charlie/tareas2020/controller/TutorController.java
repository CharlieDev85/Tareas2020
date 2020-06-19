package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.exception.TutorNotFoundException;
import com.charlie.tareas2020.model.Alumno;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.service.AlumnoService;
import com.charlie.tareas2020.service.GradoService;
import com.charlie.tareas2020.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/tutor")
public class TutorController {

    private TutorService tutorService;
    private GradoService gradoService;
    private AlumnoService alumnoService;

    @Autowired
    TutorController(TutorService tutorService, GradoService gradoService, AlumnoService alumnoService){
        this.tutorService = tutorService;
        this.gradoService = gradoService;
        this.alumnoService = alumnoService;
    }

    @GetMapping(value = "/todos")
    public ModelAndView tutorIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Tutores tutores = tutorService.getTutores();
        modelAndView.addObject("tutores", tutores);
        modelAndView.setViewName("tutor-todos");
        return modelAndView;
    }

    @GetMapping(value = "/nuevo")
    public ModelAndView tutorForm(){
        ModelAndView modelAndView = new ModelAndView();
        Alumno alumno = new Alumno(null,"", new Tutor(), new Grado());
        Grados grados = gradoService.getGrados();
        modelAndView.addObject("alumno", alumno);
        modelAndView.addObject("grados", grados);
        modelAndView.setViewName("tutor-nuevo");
        return modelAndView;
    }

    @PostMapping(value = "/nuevo")
    public ModelAndView tutorSaved(@Valid Alumno alumno, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("tutor-nuevo");
        } else {
            alumnoService.save(alumno);
            modelAndView.setViewName("redirect:/tutor/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/borrar")
    public ModelAndView tutorDelete(@Valid Tutor tutor, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        tutorService.deleteById(tutor.getId());
        modelAndView.setViewName("redirect:/tutor/todos");
        return modelAndView;
    }

    @GetMapping(value = "/modificar/{id}")
    public ModelAndView tutorModify(@PathVariable(name = "id") Long id) throws  TutorNotFoundException{
        ModelAndView modelAndView = new ModelAndView();
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if(optionalTutor.isPresent()){
            modelAndView.addObject("tutor", optionalTutor.get());
            modelAndView.setViewName("tutor-modificar");
        } else {
            modelAndView.setViewName("redirect:/tutor/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/modificar/{id}")
    public ModelAndView tutorModifyPost(@Valid Tutor tutor, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("the Valid tutor is: " + tutor);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("tutor-modificar");
        } else {
            tutorService.update(tutor);
            modelAndView.setViewName("redirect:/tutor/todos");
        }
        return modelAndView;
    }

    @GetMapping(value = "/detalles/{id}")
    public ModelAndView tutorDetallesGet(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Tutor> optionalTutor = tutorService.findById(id);
        if(optionalTutor.isPresent()){
            modelAndView.addObject("tutor", optionalTutor.get());
            modelAndView.setViewName("tutor-detalles");
        } else {
            modelAndView.setViewName("redirect:/tutor/todos");
        }
        return modelAndView;
    }

    @GetMapping(value = "/{id}/alumno/nuevo")
    public ModelAndView tutorAlumnoNuevoGet(@PathVariable(name = "id") String id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Tutor> optionalTutor = tutorService.findById(Long.parseLong(id));
        if(optionalTutor.isPresent()){
            Alumno alumno = new Alumno();
            alumno.setTutor(optionalTutor.get());
            Grados grados = gradoService.getGrados();
            modelAndView.addObject("tutor", optionalTutor.get());
            modelAndView.addObject("alumno", alumno);
            modelAndView.addObject("grados", grados);
            modelAndView.setViewName("tutor-alumno-nuevo");
        } else {
            modelAndView.setViewName("redirect:/tutor/todos");
        }
        return modelAndView;
    }

    @PostMapping(value = "/{id}/alumno/nuevo")
    public ModelAndView tutorAlumnoNuevoPost(@Valid Alumno alumno, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(!bindingResult.hasErrors()){
            alumnoService.save(alumno);
            modelAndView.setViewName("redirect:/tutor/detalles/" + alumno.getTutor().getId().toString());
        } else {
            modelAndView.setViewName("tutor-alumno-nuevo");
        }
        return modelAndView;
    }

}
