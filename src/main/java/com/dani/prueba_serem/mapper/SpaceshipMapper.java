package com.dani.prueba_serem.mapper;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.model.SpaceshipEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipMapper {

    // mapping SpaceshipEntity to Spaceship (dto)
    public Spaceship spaceshipEntityToSpaceshipMapper(SpaceshipEntity spaceshipEntity){
        Spaceship spaceship = new Spaceship();

        spaceship.setId(spaceshipEntity.getId());
        spaceship.setName(spaceshipEntity.getName());
        spaceship.setOriginType(spaceshipEntity.getOriginType());
        spaceship.setFranchise(spaceshipEntity.getFranchise());
        spaceship.setCrewCapacity(spaceshipEntity.getCrewCapacity());

        return spaceship;
    }

    // mapping Spaceship (dto) to SpaceshipEntity
    public SpaceshipEntity spaceshipToSpaceshipEntityMapper(Spaceship spaceship){
        SpaceshipEntity spaceshipEntity = new SpaceshipEntity();

        spaceshipEntity.setId(spaceship.getId());
        spaceshipEntity.setName(spaceship.getName());
        spaceshipEntity.setOriginType(spaceship.getOriginType());
        spaceshipEntity.setFranchise(spaceship.getFranchise());
        spaceshipEntity.setCrewCapacity(spaceship.getCrewCapacity());

        return spaceshipEntity;
    }
}
