package com.dani.prueba_serem.service;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.mapper.SpaceshipMapper;
import com.dani.prueba_serem.model.SpaceshipEntity;
import com.dani.prueba_serem.repository.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceshipServiceImpl implements SpaceshipService{

    public static final String SPACESHIP_ENTITY_NOT_FOUND_WITH_ID = "SpaceshipEntity not found with id: ";

    private final SpaceshipRepository spaceshipRepository;

    private final SpaceshipMapper spaceshipMapper;

    @Override
    @Cacheable("spaceships")
    public Spaceship getSpaceshipById(final Long id){

        return spaceshipRepository.findById(id)
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .orElseThrow(() -> new EntityNotFoundException(SPACESHIP_ENTITY_NOT_FOUND_WITH_ID + id));
    }

    @Override
    @Cacheable("spaceships")
    public Page<Spaceship> getSpaceships(final Pageable pageable){

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
    @Cacheable(value = "spaceships", key = "#parameter")
    public List<Spaceship> getSpaceshipsWithParameter(final String parameter) {

        List<SpaceshipEntity> spaceshipEntityList = spaceshipRepository.findByNameContainingIgnoreCase(parameter);

        return spaceshipEntityList.stream()
                .map(spaceshipMapper::spaceshipEntityToSpaceshipMapper)
                .toList();
    }

    @Override
    @CacheEvict(value = "spaceships", allEntries = true)
    public Spaceship createSpaceship(final Spaceship spaceship) {

        SpaceshipEntity spaceshipEntity = spaceshipMapper.spaceshipToSpaceshipEntityMapper(spaceship);
        SpaceshipEntity savedSpaceshipEntity = spaceshipRepository.save(spaceshipEntity);
        return spaceshipMapper.spaceshipEntityToSpaceshipMapper(savedSpaceshipEntity);
    }

    @Override
    @CacheEvict(value = "spaceships", key = "#id")
    public Spaceship updateSpaceship(final Long id, final Spaceship updatedSpaceship) {

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
    @CacheEvict(value = "spaceships", key = "#id")
    public void deleteSpaceship(final Long id) {

        SpaceshipEntity deletingSpaceshipEntity = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SPACESHIP_ENTITY_NOT_FOUND_WITH_ID + id));

        spaceshipRepository.delete(deletingSpaceshipEntity);
    }




}
