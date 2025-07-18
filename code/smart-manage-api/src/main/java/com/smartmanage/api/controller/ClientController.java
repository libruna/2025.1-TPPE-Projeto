package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.ClientRequestDto;
import com.smartmanage.api.dto.response.ClientResponseDto;
import com.smartmanage.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    public ClientService clientService;

    @PostMapping()
    public ResponseEntity<ClientResponseDto> saveClient(@RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.created(null).body(clientService.saveClient(clientRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(clientService.getClientById(id));
    }
}
