package com.charlie.tareas2020.dto;

import com.charlie.tareas2020.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tutores {
    private List<Tutor> tutores;

}
