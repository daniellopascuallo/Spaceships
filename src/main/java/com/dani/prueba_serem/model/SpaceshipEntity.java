package com.dani.prueba_serem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaceshipEntity_seq")
    @SequenceGenerator(name = "spaceshipEntity_seq", sequenceName = "spaceshipEntity_sequence", allocationSize = 1)
    private Long id;

    private String name;
    private OriginType originType;
    private String franchise;
    private Integer crewCapacity;

}
