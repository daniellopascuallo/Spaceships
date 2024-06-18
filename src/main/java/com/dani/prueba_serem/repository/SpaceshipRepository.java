package com.dani.prueba_serem.repository;

import com.dani.prueba_serem.model.SpaceshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Long> {

    // returns a list of spaceships, method accessible by default
    Page<SpaceshipEntity> findAll(Pageable pageable);

}
