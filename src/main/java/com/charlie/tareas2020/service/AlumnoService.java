package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Tutores;
import com.charlie.tareas2020.model.Alumno;
import com.charlie.tareas2020.model.Tutor;
import com.charlie.tareas2020.repository.AlumnoRepository;
import com.charlie.tareas2020.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    private AlumnoRepository alumnoRepository;


    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository){
        this.alumnoRepository = alumnoRepository;
    }



    public Alumno save(Alumno alumno){
        return alumnoRepository.save(alumno);
    }


}
