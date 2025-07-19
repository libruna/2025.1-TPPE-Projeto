package com.smartmanage.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartmanage.api.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {

    private UUID id;
    private OrderStatusEnum status;
    private BigDecimal totalPrice;
    private BigDecimal finalPrice;
    private BigDecimal discount;
    private String createdAt;
    private String updatedAt;
    private List<ItemResponseDto> items;
    private EmployeeResponseDto employee;
    private PaymentResponseDto payment;
}
