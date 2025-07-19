package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.EmployeeController;
import com.smartmanage.api.dto.request.EmployeeRequestDto;
import com.smartmanage.api.dto.request.PositionRequestDto;
import com.smartmanage.api.dto.request.RoleRequestDto;
import com.smartmanage.api.dto.response.EmployeeResponseDto;
import com.smartmanage.api.dto.response.PositionResponseDto;
import com.smartmanage.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "Should save employee: {0}")
    @MethodSource("provideEmployeesForSave")
    void saveEmployee(String name, String document, String email, String phoneNumber, UUID expectedId) {
        PositionRequestDto positionRequest = new PositionRequestDto();
        positionRequest.setId(1L);

        RoleRequestDto roleRequest = new RoleRequestDto();
        roleRequest.setId(1L);

        EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
        employeeRequest.setName(name);
        employeeRequest.setDocument(document);
        employeeRequest.setEmail(email);
        employeeRequest.setPhoneNumber(phoneNumber);
        employeeRequest.setPosition(positionRequest);
        employeeRequest.setRole(roleRequest);
        employeeRequest.setPassword("password");

        PositionResponseDto positionResponse = new PositionResponseDto();
        positionResponse.setId(1L);

        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(expectedId);
        employeeResponseDto.setName(name);
        employeeResponseDto.setDocument(document);
        employeeResponseDto.setEmail(email);
        employeeResponseDto.setPhoneNumber(phoneNumber);
        employeeResponseDto.setPosition(positionResponse);

        when(employeeService.saveEmployee(any(EmployeeRequestDto.class))).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> response = employeeController.saveEmployee(employeeRequest);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(document, response.getBody().getDocument());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(phoneNumber, response.getBody().getPhoneNumber());
        assertEquals(positionResponse, response.getBody().getPosition());
    }

    private static Stream<Arguments> provideEmployeesForSave() {
        return Stream.of(
                Arguments.of("João Batista", "12345678900", "joao@example.com", "11912345678", UUID.fromString("11111111-1111-1111-1111-111111111111")),
                Arguments.of("Maria Clara", "98765432100", "maria@example.com", "11987654321", UUID.fromString("22222222-2222-2222-2222-222222222222"))
        );
    }

    @ParameterizedTest(name = "Should return employee {1}")
    @CsvSource({
            "33333333-3333-3333-3333-333333333333, Lucas Mendes, 12345678900, lucas@example.com, 11955555555",
            "44444444-4444-4444-4444-444444444444, Ana Souza, 98765432100, ana@example.com, 11988888888"
    })
    void getEmployee(String paramId, String name, String document, String email, String phoneNumber) {
        UUID id = UUID.fromString(paramId);

        PositionResponseDto positionResponse = new PositionResponseDto();
        positionResponse.setId(1L);

        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(id);
        employeeResponseDto.setName(name);
        employeeResponseDto.setDocument(document);
        employeeResponseDto.setEmail(email);
        employeeResponseDto.setPhoneNumber(phoneNumber);
        employeeResponseDto.setPosition(positionResponse);

        when(employeeService.getEmployeeById(id)).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> response = employeeController.getEmployee(id);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(document, response.getBody().getDocument());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(phoneNumber, response.getBody().getPhoneNumber());
        assertEquals(positionResponse, response.getBody().getPosition());
    }
}