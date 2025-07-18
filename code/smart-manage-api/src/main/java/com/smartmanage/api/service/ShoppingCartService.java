package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.ShoppingCartRequestDto;
import com.smartmanage.api.dto.response.ShoppingCartResponseDto;

import java.util.UUID;

public interface ShoppingCartService {

    void updateShoppingCart(UUID clientId, ShoppingCartRequestDto shoppingCartRequestDto);
    ShoppingCartResponseDto getShoppingCartByClientId(UUID clientId, Integer discountPercentage);
}
