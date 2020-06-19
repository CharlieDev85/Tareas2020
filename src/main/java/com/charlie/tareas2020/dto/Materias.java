package com.charlie.tareas2020.dto;

import com.charlie.tareas2020.model.Materia;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor

public class Materias {
    private List<Materia> materias;

    public Materias(List<Materia> materias){
        this.materias= materias;
    }

}
