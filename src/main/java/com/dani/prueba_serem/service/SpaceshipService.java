package com.dani.prueba_serem.service;

import com.dani.prueba_serem.dto.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipService {

    Spaceship getSpaceshipById(Long id);

    Page<Spaceship> getSpaceships(Pageable pageable);


}
