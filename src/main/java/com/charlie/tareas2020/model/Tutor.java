package com.charlie.tareas2020.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty(message = "*Escribe el nombre del Tutor Legal del Alumno")
    private String nombre;
    @OneToMany(mappedBy="tutor", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Alumno> alumnos;
}
