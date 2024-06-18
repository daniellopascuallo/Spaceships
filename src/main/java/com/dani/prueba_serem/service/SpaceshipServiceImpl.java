package com.dani.prueba_serem.service;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.mapper.SpaceshipMapper;
import com.dani.prueba_serem.model.SpaceshipEntity;
import com.dani.prueba_serem.repository.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<Spaceship> getSpaceships(Pageable pageable){

        // retrieve an entity page from db, a page of entities, a list of SpaceshipEntity
        Page<SpaceshipEntity> spaceshipEntityPage = spaceshipRepository.findAll(pageable);

        // map those entities to dtos, from SpaceshipEntity to Spaceship
        List<Spaceship> spaceshipList = spaceshipEntityPage.stream()
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .toList();

        // create a new page with those dtos, PageImpl<>('content', 'pageable', 'total')
        return new PageImpl<>(spaceshipList, pageable, spaceshipEntityPage.getTotalElements());
    }


}
