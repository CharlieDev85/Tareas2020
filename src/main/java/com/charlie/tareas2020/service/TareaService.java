package com.charlie.tareas2020.service;


import com.charlie.tareas2020.model.Bimestre;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Tarea;
import com.charlie.tareas2020.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    private TareaRepository tareaRepository;

    @Autowired
    TareaService(TareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> findByMateriaAndBimestre (Materia materia, Bimestre bimestre){
        return tareaRepository.findByMateriaAndBimestre(materia,bimestre);
    }

    public void save(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }
}
