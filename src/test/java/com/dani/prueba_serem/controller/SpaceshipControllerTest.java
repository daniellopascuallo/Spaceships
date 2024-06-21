package com.dani.prueba_serem.controller;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.service.SpaceshipService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipControllerTest {

    private static final Long ID_TEST = 1L;

    @InjectMocks
    private SpaceshipController spaceshipController;

    @Mock
    private SpaceshipService spaceshipService;

    @Test
    @DisplayName("GET - /{id} - OK")
    void getSpaceshipByIdTest() {
        // Arrange
        Spaceship spaceshipTest = new Spaceship();
        when(spaceshipService.getSpaceshipById(anyLong())).thenReturn(spaceshipTest);

        // Act
        ResponseEntity<Spaceship> response = spaceshipController.getSpaceshipById(ID_TEST);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getSpaceships() {
    }

    @Test
    void getSpaceshipsWithParameter() {
    }

    @Test
    void createSpaceship() {
    }

    @Test
    void updateSpaceship() {
    }

    @Test
    void deleteSpaceship() {
    }
}