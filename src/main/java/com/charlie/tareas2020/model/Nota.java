package com.charlie.tareas2020.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Max;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Nota {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Tarea tarea;
    @ManyToOne
    private Alumno alumno;
    @Column
    @Max(100)
    private Integer punteoAsignado;
}
