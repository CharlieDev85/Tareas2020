package com.charlie.tareas2020.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profesor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @OneToMany(mappedBy="profesor", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @NotNull
    private List<Materia> materias;
    @PreRemove
    private void preRemove(){
        for(Materia m: materias){
            m.setProfesor(null);
        }
    }
}
