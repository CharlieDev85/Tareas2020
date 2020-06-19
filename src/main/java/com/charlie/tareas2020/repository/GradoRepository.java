package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Long> {

}
