package com.charlie.tareas2020.service;


import com.charlie.tareas2020.dto.Profesores;
import com.charlie.tareas2020.model.Profesor;
import com.charlie.tareas2020.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfesorService {
    private ProfesorRepository profesorRepository;

    @Autowired
    ProfesorService(ProfesorRepository profesorRepository){
        this.profesorRepository = profesorRepository;
    }

    public Profesor save(Profesor profesor){
        return profesorRepository.save(profesor);
    }

    public Profesor findLastInserted(){
        return profesorRepository.findFirstByOrderByIdDesc();
    }

    public Profesores getProfesores() {
        return new Profesores(profesorRepository.findAll());
    }
    public Optional<Profesor> findById(Long id){
        return profesorRepository.findById(id);
    }

    public void deleteById(Long id) {
        profesorRepository.deleteById(id);
    }
}
