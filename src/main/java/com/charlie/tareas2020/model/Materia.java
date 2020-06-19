package com.charlie.tareas2020.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String nombre;
    @OneToOne
    private Grado grado;
    @Column
    @NotNull
    private String materiaGrado;
    @ManyToOne(cascade=CascadeType.MERGE)
    //@JoinColumn(insertable=false, updatable=false)
    private Profesor profesor;
    @OneToMany(mappedBy="materia", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
    private List<Tarea> tareas;
}
