package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private PaymentMethodRequestDto paymentMethod;

    @NotNull(message = "Os itens do pedido são obrigatórios.")
    private List<ItemRequestDto> items;
}
