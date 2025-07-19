package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.ShoppingCartRequestDto;
import com.smartmanage.api.dto.response.ShoppingCartResponseDto;
import com.smartmanage.api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PatchMapping("/clients/{clientId}")
    public ResponseEntity<Void> updateShoppingCart(@PathVariable UUID clientId,
                                                   @RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        shoppingCartService.updateShoppingCart(clientId, shoppingCartRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ShoppingCartResponseDto> getClientShoppingCart(
            @PathVariable UUID clientId,
            @RequestParam(required = false) Integer discountPercentage) {
        return ResponseEntity.ok().body(
                shoppingCartService.getShoppingCartByClientId(clientId, discountPercentage));
    }
}
