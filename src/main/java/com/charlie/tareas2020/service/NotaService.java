package com.charlie.tareas2020.service;


import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Nota;
import com.charlie.tareas2020.model.Tarea;
import com.charlie.tareas2020.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {
    private NotaRepository notaRepository;

    @Autowired
    NotaService(NotaRepository notaRepository){
        this.notaRepository = notaRepository;
    }

    public List<Nota> findByTarea(Tarea tarea) {
        return notaRepository.findByTarea(tarea);
    }

    public void save(Nota nota) {
        notaRepository.save(nota);
    }
}
