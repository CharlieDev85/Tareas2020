package com.charlie.tareas2020.dto;

import com.charlie.tareas2020.model.Profesor;
import lombok.*;

import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Profesores {
    private List<Profesor> profesores;
    public Profesores(List<Profesor> profesores){
        this.profesores = profesores;
    }
}
