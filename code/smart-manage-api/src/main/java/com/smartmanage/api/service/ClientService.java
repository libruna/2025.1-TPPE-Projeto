package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.ClientRequestDto;
import com.smartmanage.api.dto.response.ClientResponseDto;

import java.util.UUID;

public interface ClientService {

    ClientResponseDto saveClient(ClientRequestDto clientRequestDto);
    ClientResponseDto getClientById(UUID id);
}
