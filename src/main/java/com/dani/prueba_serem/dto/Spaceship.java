package com.dani.prueba_serem.dto;

import com.dani.prueba_serem.model.OriginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spaceship {

    private Long id;
    private String name;
    private OriginType originType;
    private String franchise;
    private Integer crewCapacity;

}
