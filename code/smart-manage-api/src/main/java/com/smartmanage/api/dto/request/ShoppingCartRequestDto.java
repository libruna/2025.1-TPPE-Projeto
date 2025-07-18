package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShoppingCartRequestDto {

    @NotNull(message = "O item é obrigatório.")
    private ItemRequestDto item;
}
