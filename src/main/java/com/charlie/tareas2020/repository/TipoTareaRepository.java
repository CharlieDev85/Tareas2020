package com.charlie.tareas2020.repository;

import com.charlie.tareas2020.model.TipoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTareaRepository extends JpaRepository<TipoTarea, Long> {

}
