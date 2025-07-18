package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O documento é obrigatório.")
    private String document;

    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "O número de telefone é obrigatório.")
    private String phoneNumber;

    @NotNull(message = "Informar se o cliente é um membro associado é obrigatório.")
    private Boolean associateMember;
}
