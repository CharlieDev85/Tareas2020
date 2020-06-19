package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Materia findFirstByOrderByIdDesc();
    List<Materia> findAllByProfesorIsNull();
}
