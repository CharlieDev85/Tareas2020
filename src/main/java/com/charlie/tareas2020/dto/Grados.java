package com.charlie.tareas2020.dto;

import com.charlie.tareas2020.model.Grado;

import java.util.List;

public class Grados {
    private List<Grado> grados;

    public Grados() {
    }

    public Grados(List<Grado> grados) {
        this.grados = grados;
    }

    public List<Grado> getGrados() {
        return grados;
    }

    public void setGrados(List<Grado> grados) {
        this.grados = grados;
    }
}
