package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.ClientRequestDto;
import com.smartmanage.api.dto.response.ClientResponseDto;
import com.smartmanage.api.model.entity.Client;
import com.smartmanage.api.repository.ClientRepository;
import com.smartmanage.api.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper mapper;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ClientResponseDto saveClient(ClientRequestDto clientRequestDto) {
        clientRepository.findByDocument(clientRequestDto.getDocument())
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado.");
                });

        return ClientResponseDto.builder()
                .id(this.clientRepository.save(this.mapper.map(clientRequestDto, Client.class)).getId())
                .build();
    }

    @Override
    public ClientResponseDto getClientById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        return mapper.map(client, ClientResponseDto.class);
    }
}
