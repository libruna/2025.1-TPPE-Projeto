package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

    @NotBlank(message = "O CPF é obrigatório.")
    private String document;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}
