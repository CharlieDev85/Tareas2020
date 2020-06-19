package com.charlie.tareas2020.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bimestre {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column
    private String nombre;
}
