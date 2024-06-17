package com.dani.prueba_serem.repository;

import com.dani.prueba_serem.model.SpaceshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Long> {

}
