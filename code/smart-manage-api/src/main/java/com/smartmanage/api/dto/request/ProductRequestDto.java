package com.smartmanage.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotNull(message = "O valor é obrigatório.")
    private BigDecimal price;

    private String barCode;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    private Long stock;

    @NotNull(message = "As categorias do produto são obrigatórias.")
    private List<Long> categories;
}
