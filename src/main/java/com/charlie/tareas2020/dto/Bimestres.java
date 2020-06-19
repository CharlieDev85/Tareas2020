package com.charlie.tareas2020.dto;

import com.charlie.tareas2020.model.Bimestre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Bimestres {
    private List<Bimestre> bimestres;

    public Bimestres(List<Bimestre> bimestres){
        this.bimestres = bimestres;
    }
}
