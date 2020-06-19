package com.charlie.tareas2020.service;

import com.charlie.tareas2020.dto.Bimestres;
import com.charlie.tareas2020.model.Bimestre;
import com.charlie.tareas2020.repository.BimestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BimestreService {
    private BimestreRepository bimestreRepository;

    @Autowired
    public BimestreService(BimestreRepository bimestreRepository){
        this.bimestreRepository = bimestreRepository;
    }

    public Bimestres getBimestres(){
        Bimestres bimestres = new Bimestres(bimestreRepository.findAll());
        return bimestres;
    }

    public Optional<Bimestre> findById(Long id){
        return bimestreRepository.findById(id);
    }

}
