package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ItemRequestDto {

    @NotNull(message = "O identificador do produto é obrigatório.")
    private UUID productId;

    @NotNull(message = "A quantidade é obrigatória.")
    private Integer number;

    private Integer discountPercentage;
}
