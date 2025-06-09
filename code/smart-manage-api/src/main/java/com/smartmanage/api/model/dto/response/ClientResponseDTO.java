package com.smartmanage.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO {

    private String id;
    private String name;

    public ClientResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
