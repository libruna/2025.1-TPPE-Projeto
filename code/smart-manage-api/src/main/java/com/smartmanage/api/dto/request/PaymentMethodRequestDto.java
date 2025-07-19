package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentMethodRequestDto {

    @NotNull(message = "O identificador do método de pagamento é obrigatório.")
    private Long id;
}