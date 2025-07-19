package com.smartmanage.api.unit.controller;

import com.smartmanage.api.controller.ShoppingCartController;
import com.smartmanage.api.dto.request.ItemRequestDto;
import com.smartmanage.api.dto.request.ShoppingCartRequestDto;
import com.smartmanage.api.dto.response.ItemResponseDto;
import com.smartmanage.api.dto.response.ShoppingCartResponseDto;
import com.smartmanage.api.service.ShoppingCartService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Random.class)
public class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "Should update shopping cart for client ID: {0}")
    @MethodSource("provideCartUpdates")
    void updateShoppingCart(UUID clientId, ItemRequestDto item) {
        ShoppingCartRequestDto shoppingCartRequest = new ShoppingCartRequestDto();
        shoppingCartRequest.setItem(item);

        doNothing().when(shoppingCartService).updateShoppingCart(eq(clientId), any(ShoppingCartRequestDto.class));

        ResponseEntity<Void> response = shoppingCartController.updateShoppingCart(clientId, shoppingCartRequest);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(shoppingCartService, times(1)).updateShoppingCart(eq(clientId), any());
    }

    private static Stream<Arguments> provideCartUpdates() {
        UUID productId = UUID.randomUUID();

        return Stream.of(
                Arguments.of(UUID.randomUUID(), ItemRequestDto.builder().productId(productId).number(2).build()),
                Arguments.of(UUID.randomUUID(), ItemRequestDto.builder().productId(productId).number(3).build())
        );
    }

    @ParameterizedTest(name = "Should return shopping cart for client ID: {0} with {2} item(s)")
    @MethodSource("provideCarts")
    void getClientShoppingCart(UUID clientId, ShoppingCartResponseDto mockResponse, int itemCount) {
        when(shoppingCartService.getShoppingCartByClientId(eq(clientId), any())).thenReturn(mockResponse);

        ResponseEntity<ShoppingCartResponseDto> response = shoppingCartController.getClientShoppingCart(clientId, mockResponse.getDiscountPercentage());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemCount, response.getBody().getItems().size());
        assertEquals(mockResponse.getTotalPrice(), response.getBody().getTotalPrice());
    }

    private static Stream<Arguments> provideCarts() {
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        ShoppingCartResponseDto cartWithOneItem = new ShoppingCartResponseDto();
        cartWithOneItem.setId(cartId);
        cartWithOneItem.setTotalPrice(new BigDecimal("100.00"));
        cartWithOneItem.setDiscountPercentage(10);
        cartWithOneItem.setItems(List.of(
                ItemResponseDto.builder()
                        .productId(productId)
                        .number(2)
                        .totalPrice(100L)
                        .build()
        ));

        ShoppingCartResponseDto cartWithNoItems = new ShoppingCartResponseDto();
        cartWithNoItems.setId(cartId);
        cartWithNoItems.setTotalPrice(BigDecimal.ZERO);
        cartWithNoItems.setDiscountPercentage(0);
        cartWithNoItems.setItems(List.of());

        return Stream.of(
                Arguments.of(UUID.randomUUID(), cartWithOneItem, 1),
                Arguments.of(UUID.randomUUID(), cartWithNoItems, 0)
        );
    }
}