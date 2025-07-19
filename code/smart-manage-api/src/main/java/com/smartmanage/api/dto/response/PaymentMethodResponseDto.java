package com.smartmanage.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartmanage.api.enums.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethodResponseDto {

    private Long id;
    private PaymentMethodEnum name;
}
