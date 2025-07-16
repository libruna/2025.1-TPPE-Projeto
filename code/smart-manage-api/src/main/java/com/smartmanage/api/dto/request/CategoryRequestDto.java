package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório.")
    private String name;
    private String description;
}
