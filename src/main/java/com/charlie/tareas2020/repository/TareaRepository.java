package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Bimestre;
import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByMateriaAndBimestre(Materia materia, Bimestre bimestre);
}

