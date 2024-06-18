package com.dani.prueba_serem.controller;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.exception.custom.InvalidPageParameterException;
import com.dani.prueba_serem.service.SpaceshipService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
        try {
            Spaceship spaceship = spaceshipService.getSpaceshipById(id);
            return ResponseEntity.ok(spaceship);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Page<Spaceship> getSpaceships(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {

        if (page < 0) {
            throw new InvalidPageParameterException("page number cannot be negative");
        }
        if (size <= 0 || size > 100) {
            throw new InvalidPageParameterException("page size must be between 1 and 100");
        }

        PageRequest pageable = PageRequest.of(page, size);
        return spaceshipService.getSpaceships(pageable);
    }

}
