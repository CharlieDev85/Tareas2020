package com.charlie.tareas2020.service;

import com.charlie.tareas2020.model.TipoTarea;
import com.charlie.tareas2020.repository.TipoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTareaService {
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    TipoTareaService(TipoTareaRepository tipoTareaRepository){this.tipoTareaRepository = tipoTareaRepository;}

    public List<TipoTarea> findAll(){
        return tipoTareaRepository.findAll();
    }

}
