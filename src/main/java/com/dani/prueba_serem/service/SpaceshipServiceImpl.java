package com.dani.prueba_serem.service;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.mapper.SpaceshipMapper;
import com.dani.prueba_serem.repository.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipServiceImpl implements SpaceshipService{

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapper spaceshipMapper;

    @Override
    public Spaceship getSpaceshipById(Long id){

        return spaceshipRepository.findById(id)
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .orElseThrow(() -> new EntityNotFoundException("SpaceshipEntity not found with id: " + id));
    }


}
