package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PositionRequestDto {

    @NotNull(message = "O identificador é obrigatório.")
    private Long id;
}
