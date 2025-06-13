package com.smartmanage.api.service;

import com.smartmanage.api.dto.response.ClientResponseDTO;

public interface ClientService {

    ClientResponseDTO getClientById(String id);
}
