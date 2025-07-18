package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;
    private String description;
}
