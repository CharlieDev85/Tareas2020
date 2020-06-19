package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Materias;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MateriaService {

    private MateriaRepository materiaRepository;

    @Autowired
    MateriaService(MateriaRepository materiaRepository){
        this.materiaRepository = materiaRepository;
    }

    public Materias getMaterias(){
        Materias materias = new Materias(materiaRepository.findAll());
        return materias;
    }

    public Materia save(Materia materia){
        return materiaRepository.save(materia);
    }

    public void deleteById(Long id){
        materiaRepository.deleteById(id);
    }

    public Optional<Materia> findById(Long id){
        /*
        get(): If a value is present in this Optional,
        returns the value, otherwise throws NoSuchElementException.
        */
        return materiaRepository.findById(id);
    }

    public Materia findLastInserted (){
        return materiaRepository.findFirstByOrderByIdDesc();
    }

    public Materias getMateriasWithNoProfesor() {
        return new Materias(materiaRepository.findAllByProfesorIsNull());
    }
}
