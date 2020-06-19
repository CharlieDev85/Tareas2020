package com.charlie.tareas2020.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String tema;
    @Column
    private String instrucciones;
    @ManyToOne
    private Materia materia;
    @ManyToOne
    private TipoTarea tipoTarea;
    @Column
    private Integer punteoMaximo;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // or use @DateTimeFormat(pattern = DateTimeFormat.ISO.DATE)
    private Date fechaDeEntrega;
    @ManyToOne
    private Bimestre bimestre;

}
