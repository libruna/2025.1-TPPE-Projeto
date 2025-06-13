package com.smartmanage.api.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private final int statusCode;
    private final String message;
}
