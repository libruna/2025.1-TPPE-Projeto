package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.ClientController;
import com.smartmanage.api.dto.request.ClientRequestDto;
import com.smartmanage.api.dto.response.ClientResponseDto;
import com.smartmanage.api.service.ClientService;
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
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "Should save client: {0}")
    @MethodSource("provideClientsForSave")
    void saveClient(String name, String document, String email, String phone, Boolean associateMember, UUID expectedId) {
        ClientRequestDto clientRequest = new ClientRequestDto();
        clientRequest.setName(name);
        clientRequest.setDocument(document);
        clientRequest.setEmail(email);
        clientRequest.setPhoneNumber(phone);
        clientRequest.setAssociateMember(associateMember);

        ClientResponseDto clientResponse = ClientResponseDto.builder()
                .id(expectedId)
                .name(name)
                .document(document)
                .email(email)
                .phoneNumber(phone)
                .associateMember(associateMember)
                .build();

        when(clientService.saveClient(any(ClientRequestDto.class))).thenReturn(clientResponse);

        ResponseEntity<ClientResponseDto> response = clientController.saveClient(clientRequest);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(document, response.getBody().getDocument());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(phone, response.getBody().getPhoneNumber());
        assertEquals(associateMember, response.getBody().getAssociateMember());
    }

    private static Stream<Arguments> provideClientsForSave() {
        return Stream.of(
                Arguments.of("Ana Souza", "12345678901", "ana@example.com", "11999990000", true, UUID.fromString("11111111-1111-1111-1111-111111111111")),
                Arguments.of("Carlos Dias", "98765432100", "carlos@example.com", "21988880000", false, UUID.fromString("22222222-2222-2222-2222-222222222222"))
        );
    }

    @ParameterizedTest(name = "Should return client: {1}")
    @CsvSource({
            "33333333-3333-3333-3333-333333333333, Maria Lima, 11122233344, maria@example.com, 11912345678, true",
            "44444444-4444-4444-4444-444444444444, José Costa, 22233344455, jose@example.com, 11987654321, false"
    })
    void getClient(String paramId, String name, String document, String email, String phone, Boolean associateMember) {
        UUID id = UUID.fromString(paramId);

        ClientResponseDto clientResponse = ClientResponseDto.builder()
                .id(id)
                .name(name)
                .document(document)
                .email(email)
                .phoneNumber(phone)
                .associateMember(associateMember)
                .build();

        when(clientService.getClientById(id)).thenReturn(clientResponse);

        ResponseEntity<ClientResponseDto> response = clientController.getClient(id);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(document, response.getBody().getDocument());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(phone, response.getBody().getPhoneNumber());
        assertEquals(associateMember, response.getBody().getAssociateMember());
    }
}
