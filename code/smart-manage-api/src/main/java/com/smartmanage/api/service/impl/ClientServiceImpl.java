package com.smartmanage.api.service.impl;

import com.smartmanage.api.model.dto.response.ClientResponseDTO;
import com.smartmanage.api.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientResponseDTO getClientById(String id) {
        return new ClientResponseDTO("123", "John");
    }
}
