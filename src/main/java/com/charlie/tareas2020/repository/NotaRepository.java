package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Materia;
import com.charlie.tareas2020.model.Nota;
import com.charlie.tareas2020.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {


    List<Nota> findByTarea(Tarea tarea);
}
