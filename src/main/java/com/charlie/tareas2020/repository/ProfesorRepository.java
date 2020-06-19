package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Profesor findFirstByOrderByIdDesc();
}
