package com.charlie.tareas2020.controller;

import com.charlie.tareas2020.dto.Bimestres;
import com.charlie.tareas2020.dto.Profesores;
import com.charlie.tareas2020.model.*;
import com.charlie.tareas2020.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profesor/tarea")
public class ProfesorTareaController {
    private ProfesorService profesorService;
    private BimestreService bimestreService;
    private MateriaService materiaService;
    private TareaService tareaService;
    private TipoTareaService tipoTareaService;

    @Autowired
    ProfesorTareaController(ProfesorService profesorService
            ,BimestreService bimestreService
            , MateriaService materiaService
            ,TareaService tareaService
            ,TipoTareaService tipoTareaService){
        this.profesorService = profesorService;
        this.bimestreService = bimestreService;
        this.materiaService = materiaService;
        this.tareaService = tareaService;
        this.tipoTareaService = tipoTareaService;
    }

    @GetMapping("/paso1")
    public ModelAndView paso1Get(){
        ModelAndView modelAndView = new ModelAndView();
        Profesores profesores = profesorService.getProfesores();
        modelAndView.addObject("profesores", profesores);
        modelAndView.setViewName("profesor-tarea-paso1");
        return modelAndView;
    }

    @GetMapping("/paso2")
    public ModelAndView paso2Get(@RequestParam("profesor.id") String profesorId){
        ModelAndView modelAndView = new ModelAndView();
        Bimestres bimestres = bimestreService.getBimestres();
        Optional<Profesor> profesor = profesorService.findById(Long.valueOf(profesorId));
        if(profesor.isPresent()){
            modelAndView.addObject("profesor", profesor.get());
            modelAndView.addObject("bimestres", bimestres);
            modelAndView.setViewName("profesor-tarea-paso2");
        }    else {
            modelAndView.setViewName("redirect:/profesor/tarea/paso1");
        }
        return modelAndView;
    }

    @GetMapping("/paso3")
    public ModelAndView paso3Get(@RequestParam("materia.id") String materiaId,
                                 @RequestParam("bimestre.id") String bimestreId ){
        ModelAndView modelAndView = new ModelAndView();
        Materia materia = materiaService.findById(Long.parseLong(materiaId)).get();
        Bimestre bimestre = bimestreService.findById(Long.parseLong(bimestreId)).get();
        List<Tarea> tareas = tareaService.findByMateriaAndBimestre(materia, bimestre);
        List<TipoTarea> tiposTarea = tipoTareaService.findAll();
        if (tareas.size() <= 0) {
            Tarea tarea = new Tarea();
            tarea.setMateria(materia);
            tarea.setBimestre(bimestre);
            tareas.add(tarea);
        }
        Tarea emptyTareaWithMateriaAndBimestre = new Tarea();
        emptyTareaWithMateriaAndBimestre.setMateria(materia);
        emptyTareaWithMateriaAndBimestre.setBimestre(bimestre);
        modelAndView.addObject("tareas", tareas);
        modelAndView.addObject("emptyTarea", emptyTareaWithMateriaAndBimestre);
        modelAndView.addObject("tiposTarea", tiposTarea);
        modelAndView.setViewName("profesor-tarea-paso3");
        return modelAndView;
    }

    @PostMapping("/paso3")
    public ModelAndView paso3Post(@Valid Tarea tarea, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(!bindingResult.hasErrors()){
            tareaService.save(tarea);
            modelAndView.setViewName("redirect:/profesor/todos");
        }else{
            modelAndView.setViewName("profesor-tarea-paso3");
        }
        return modelAndView;
    }


}
