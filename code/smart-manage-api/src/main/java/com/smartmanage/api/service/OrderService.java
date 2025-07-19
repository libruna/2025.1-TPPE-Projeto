package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.OrderRequestDto;
import com.smartmanage.api.dto.response.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto saveOrder(UUID clientId, OrderRequestDto orderRequestDto, String employeeId);
    List<OrderResponseDto> findAllByClientId(UUID clientId);
}
