package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O documento é obrigatório.")
    private String document;

    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "O número de telefone é obrigatório.")
    private String phoneNumber;

    @NotNull(message = "O cargo é obrigatório.")
    private PositionRequestDto position;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;

    @NotNull(message = "O nível de acesso é obrigatório.")
    private RoleRequestDto role;
}
