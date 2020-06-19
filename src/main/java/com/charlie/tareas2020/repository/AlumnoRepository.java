package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Alumno;
import com.charlie.tareas2020.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
