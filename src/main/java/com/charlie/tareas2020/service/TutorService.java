package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TutorService {
    private TutorRepository tutorRepository;


    @Autowired
    public TutorService(TutorRepository tutorRepository){
        this.tutorRepository = tutorRepository;
    }

    public Tutores getTutores(){
        Tutores tutores = new Tutores();
        List<Tutor> tutoresList = tutorRepository.findAll();
        tutores.setTutores(tutoresList);
        return tutores;
    }

    public Tutor save(Tutor tutor){
        return tutorRepository.save(tutor);
    }

    public Tutor update(Tutor tutor){
        /*
        https://www.tutorialspoint.com/difference-between-save-and-saveandflush-in-spring-java
         */
        return tutorRepository.saveAndFlush(tutor);
    }

    public void deleteById(Long id){
        tutorRepository.deleteById(id);
    }

    public Optional<Tutor> findById(Long id){
        /*
        get(): If a value is present in this Optional,
        returns the value, otherwise throws NoSuchElementException.
        */
        return tutorRepository.findById(id);
    }
}
