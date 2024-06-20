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

@Service
public class SpaceshipServiceImpl implements SpaceshipService{

    public static final String SPACESHIP_ENTITY_NOT_FOUND_WITH_ID = "SpaceshipEntity not found with id: ";

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapper spaceshipMapper;

    @Override
    public Spaceship getSpaceshipById(Long id){

        return spaceshipRepository.findById(id)
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .orElseThrow(() -> new EntityNotFoundException(SPACESHIP_ENTITY_NOT_FOUND_WITH_ID + id));
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

    @Override
    public List<Spaceship> getSpaceshipsWithParameter(String parameter) {

        List<SpaceshipEntity> spaceshipEntityList = spaceshipRepository.findByNameContainingIgnoreCase(parameter);

        return spaceshipEntityList.stream()
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .toList();
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) {

        SpaceshipEntity spaceshipEntity = spaceshipMapper.spaceshipToSpaceshipEntityMapper(spaceship);
        SpaceshipEntity savedSpaceshipEntity = spaceshipRepository.save(spaceshipEntity);
        return spaceshipMapper.spaceshipEntityToSpaceshipMapper(savedSpaceshipEntity);
    }

    @Override
    public Spaceship updateSpaceship(Long id, Spaceship updatedSpaceship) {

        // confirm spaceship is in db
        SpaceshipEntity existingSpaceshipEntity = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SPACESHIP_ENTITY_NOT_FOUND_WITH_ID + id));

        // map updatedSpaceship data to updatedSpaceshipEntity
        SpaceshipEntity updatedSpaceshipEntity = spaceshipMapper.spaceshipToSpaceshipEntityMapper(updatedSpaceship);

        // copy updated data to existingSpaceshipEntity
        existingSpaceshipEntity.setName(updatedSpaceshipEntity.getName());
        existingSpaceshipEntity.setOriginType(updatedSpaceshipEntity.getOriginType());
        existingSpaceshipEntity.setFranchise(updatedSpaceshipEntity.getFranchise());
        existingSpaceshipEntity.setCrewCapacity(updatedSpaceshipEntity.getCrewCapacity());

        // save updated existing spaceshipEntity to db
        SpaceshipEntity savedSpaceshipEntity = spaceshipRepository.save(existingSpaceshipEntity);

        // retrieve updated spaceshipEntity mapped to the updated spaceship
        return spaceshipMapper.spaceshipEntityToSpaceshipMapper(savedSpaceshipEntity);
    }

    @Override
    public void deleteSpaceship(Long id) {

        SpaceshipEntity deletingSpaceshipEntity = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SPACESHIP_ENTITY_NOT_FOUND_WITH_ID + id));

        spaceshipRepository.delete(deletingSpaceshipEntity);
    }




}
