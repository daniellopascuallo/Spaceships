package com.dani.prueba_serem.service;

import com.dani.prueba_serem.dto.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpaceshipService {

    Spaceship getSpaceshipById(Long id);

    Page<Spaceship> getSpaceships(Pageable pageable);

    List<Spaceship> getSpaceshipsWithParameter(String parameter);

    Spaceship createSpaceship(Spaceship spaceship);

    Spaceship updateSpaceship(Long id, Spaceship updatedSpaceship);

    void deleteSpaceship(Long id);


}
