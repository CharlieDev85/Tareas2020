package com.charlie.tareas2020.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Grado {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column
    @NotEmpty(message = "*Escribe el nombre corto del Grado")
    @Size(max = 6, message = "* Debe tener maximo 6 letras")
    private String nombreCorto;
    @Column
    @NotEmpty(message = "*Escribe el nombre del Grado")
    @Size(max = 30,  message = "* Debe tener maximo 30 letras")
    private String nombre;
    @OneToMany(mappedBy="grado", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
    private List<Alumno> alumnos;
}
