package com.charlie.tareas2020.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    private Tutor tutor;
    @ManyToOne
    private Grado grado;
}
