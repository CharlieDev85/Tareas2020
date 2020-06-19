package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Grados;
import com.charlie.tareas2020.model.Grado;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradoService {
    private GradoRepository gradoRepository;

    @Autowired
    public GradoService (GradoRepository gradoRepository){
        this.gradoRepository = gradoRepository;
    }

    public Grados getGrados(){
        Grados grados = new Grados(gradoRepository.findAll());
        return grados;
    }

    public Grado save(Grado grado){
        return gradoRepository.save(grado);
    }

    public void deleteById(Long id){
        gradoRepository.deleteById(id);
    }

    public Optional<Grado> findById(Long id){
        /*
        get(): If a value is present in this Optional,
        returns the value, otherwise throws NoSuchElementException.
        */
        return gradoRepository.findById(id);
    }

}
