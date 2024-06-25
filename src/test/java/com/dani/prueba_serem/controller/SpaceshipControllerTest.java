package com.dani.prueba_serem.controller;

import com.dani.prueba_serem.dto.Spaceship;
import com.dani.prueba_serem.exception.custom.InvalidPageParameterException;
import com.dani.prueba_serem.exception.global.GlobalExceptionHandler;
import com.dani.prueba_serem.service.SpaceshipService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipControllerTest {

    private static final Long ID_TEST = 1L;
    private static final int PAGE_TEST = 0;
    private static final int SIZE_TEST = 5;
    private static final int INVALID_PAGE_TEST = -1;
    private static final int INVALID_SIZE_SMALL_TEST = 0;
    private static final int INVALID_SIZE_LARGE_TEST = 101;
    private static final String PARAMETER = "wing";

    @InjectMocks
    private SpaceshipController spaceshipController;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private SpaceshipService spaceshipService;

    // Test to confirm we get 200 OK code
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

    // Test to confirm we throw EntityNotFoundException
    @Test
    @DisplayName("GET - /{id} - EntityNotFoundException")
    void getSpaceshipByIdEntityNotFoundExceptionTest() {

        when(spaceshipService.getSpaceshipById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            spaceshipController.getSpaceshipById(ID_TEST);});
    }

    // Test to confirm we get answer 404 not found
    @Test
    @DisplayName("GET - /{id} - Not Found")
    void getSpaceshipByIdNotFoundTest() {

        when(spaceshipService.getSpaceshipById(anyLong())).thenThrow(EntityNotFoundException.class);

        try {
            spaceshipController.getSpaceshipById(ID_TEST);
        } catch (EntityNotFoundException exception) {
            WebRequest webRequest = mock(WebRequest.class);
            ResponseEntity<String> response = globalExceptionHandler.handleEntityNotFoundException(exception, webRequest);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    // Test to confirm we get 200 OK
    @Test
    @DisplayName("GET - /page - OK")
    void getSpaceshipsTest() {

        List<Spaceship> spaceshipListTest = Collections.singletonList(new Spaceship());
        Page<Spaceship> pageTest = new PageImpl<>(spaceshipListTest);
        when(spaceshipService.getSpaceships(any(Pageable.class))).thenReturn(pageTest);

        ResponseEntity<Page<Spaceship>> response = spaceshipController.getSpaceships(PAGE_TEST, SIZE_TEST);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceshipListTest, Objects.requireNonNull(response.getBody()).getContent());
    }

    // Test to confirm we throw InvalidPageParameterException when page < 0
    @Test
    @DisplayName("GET - /page - InvalidPageParameterException page < 0")
    void getSpaceshipsInvalidPageTest() {

        assertThrows(InvalidPageParameterException.class, () -> {
            spaceshipController.getSpaceships(INVALID_PAGE_TEST, SIZE_TEST);});
    }

    // Test to confirm we throw InvalidPageParameterException when size <= 0
    @Test
    @DisplayName("GET - /page - InvalidPageParameterException size <= 0")
    void getSpaceshipsInvalidSizeSmallTest() {

        assertThrows(InvalidPageParameterException.class, () -> {
            spaceshipController.getSpaceships(PAGE_TEST, INVALID_SIZE_SMALL_TEST);
        });
    }

    // Test to confirm we throw InvalidPageParameterException when size > 100
    @Test
    @DisplayName("GET - /page - InvalidPageParameterException size > 100")
    void getSpaceshipsInvalidSizeLargeTest() {

        assertThrows(InvalidPageParameterException.class, () -> {
            spaceshipController.getSpaceships(PAGE_TEST, INVALID_SIZE_LARGE_TEST);
        });
    }

    // Test to confirm we get 400 Bad Request
    @Test
    @DisplayName("GET - /page - Bad Request")
    void getSpaceshipsBadRequestTest() {

        when(spaceshipService.getSpaceships(any(Pageable.class))).thenThrow(InvalidPageParameterException.class);

        try {
            spaceshipController.getSpaceships(PAGE_TEST, SIZE_TEST);
        } catch (InvalidPageParameterException exception) {
            WebRequest webRequest = mock(WebRequest.class);
            ResponseEntity<String> response = globalExceptionHandler.handleInvalidPageParameterException(exception, webRequest);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    // Test to confirm we get 200 OK
    @Test
    @DisplayName("GET -  - OK")
    void getSpaceshipsWithParameterTest() {

        List<Spaceship> spaceshipListTest = Collections.singletonList(new Spaceship());
        when(spaceshipService.getSpaceshipsWithParameter(anyString())).thenReturn(spaceshipListTest);

        ResponseEntity<List<Spaceship>> response = spaceshipController.getSpaceshipsWithParameter(PARAMETER);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test to confirm we get 204 no content
    @Test
    @DisplayName("GET -  - 204 No Content")
    void getSpaceshipsWithParameterNoContentTest() {

        List<Spaceship> spaceshipListTest = new ArrayList<>();
        when(spaceshipService.getSpaceshipsWithParameter(anyString())).thenReturn(spaceshipListTest);

        ResponseEntity<List<Spaceship>> response = spaceshipController.getSpaceshipsWithParameter(PARAMETER);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test to confirm we get 201 Created
    @Test
    @DisplayName("POST -  - 201 Created")
    void createSpaceshipTest() throws URISyntaxException {

        Spaceship spaceshipTest = new Spaceship();
        Spaceship createdSpaceshipTest = new Spaceship();
        URI expectedLocation = new URI("/api/spaceships/" + createdSpaceshipTest.getId());
        when(spaceshipService.createSpaceship(any(Spaceship.class))).thenReturn(createdSpaceshipTest);

        ResponseEntity<Spaceship> response = spaceshipController.createSpaceship(spaceshipTest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdSpaceshipTest, response.getBody());
        assertEquals(expectedLocation, response.getHeaders().getLocation());
    }

    // Test to confirm we throw IllegalArgumentException
    @Test
    @DisplayName("POST - - IllegalArgumentException")
    void createSpaceshipIllegalArgumentExceptionTest() {

        Spaceship invalidSpaceship = new Spaceship();
        when(spaceshipService.createSpaceship(any(Spaceship.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            spaceshipController.createSpaceship(invalidSpaceship);});
    }

    // Test to confirm we get 400 Bad Request
    @Test
    @DisplayName("POST - - 400 Bad Request")
    void createSpaceshipBadRequestTest() throws URISyntaxException {

        when(spaceshipService.createSpaceship(any(Spaceship.class))).thenThrow(IllegalArgumentException.class);

        try {
            spaceshipController.createSpaceship(new Spaceship());
        }catch(IllegalArgumentException exception) {
            WebRequest webRequest = mock(WebRequest.class);
            ResponseEntity<String> response = globalExceptionHandler.handleIllegalArgumentException(exception, webRequest);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }


    @Test
    void updateSpaceshipTest() {
    }

    @Test
    void deleteSpaceshipTest() {
    }
}