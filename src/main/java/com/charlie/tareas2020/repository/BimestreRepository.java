package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.Bimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BimestreRepository extends JpaRepository<Bimestre, Long> {

}
