package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.OrderRequestDto;
import com.smartmanage.api.dto.response.OrderResponseDto;
import com.smartmanage.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/clients/{clientId}")
    public ResponseEntity<OrderResponseDto> saveOrder(@PathVariable UUID clientId,
                                                      @RequestBody OrderRequestDto orderRequestDto,
                                                      @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.created(null).body(orderService.saveOrder(clientId, orderRequestDto, jwt.getSubject()));
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<OrderResponseDto>> getClientOrdersHistory(@PathVariable UUID clientId) {
        return ResponseEntity.ok().body(orderService.findAllByClientId(clientId));
    }
}
