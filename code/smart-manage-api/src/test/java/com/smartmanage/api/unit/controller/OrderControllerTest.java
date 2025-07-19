package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.OrderController;
import com.smartmanage.api.dto.request.OrderRequestDto;
import com.smartmanage.api.dto.response.OrderResponseDto;
import com.smartmanage.api.service.OrderService;
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
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    static Stream<Arguments> provideOrdersForSave() {
        return Stream.of(
                Arguments.of(UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")),
                Arguments.of(UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
        );
    }

    @ParameterizedTest(name = "Should save order for client {0} by employee {1}")
    @MethodSource("provideOrdersForSave")
    void saveOrder(UUID clientId, UUID employeeId) {
        OrderRequestDto orderRequest = new OrderRequestDto();

        OrderResponseDto expectedResponse = new OrderResponseDto();
        expectedResponse.setId(UUID.randomUUID());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn(employeeId.toString());
        when(orderService.saveOrder(eq(clientId), any(OrderRequestDto.class), eq(employeeId.toString())))
                .thenReturn(expectedResponse);

        ResponseEntity<OrderResponseDto> response = orderController.saveOrder(clientId, orderRequest, jwt);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderService, times(1)).saveOrder(eq(clientId), any(OrderRequestDto.class), eq(employeeId.toString()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "33333333-3333-3333-3333-333333333333",
            "44444444-4444-4444-4444-444444444444"
    })
    void getClientOrdersHistory(String clientIdStr) {
        UUID clientId = UUID.fromString(clientIdStr);

        List<OrderResponseDto> orders = Arrays.asList(
                new OrderResponseDto() {{ setId(UUID.randomUUID()); }},
                new OrderResponseDto() {{ setId(UUID.randomUUID()); }}
        );

        when(orderService.findAllByClientId(clientId)).thenReturn(orders);

        ResponseEntity<List<OrderResponseDto>> response = orderController.getClientOrdersHistory(clientId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).findAllByClientId(clientId);
    }
}