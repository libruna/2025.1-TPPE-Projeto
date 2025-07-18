package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.AuthRequestDto;
import com.smartmanage.api.dto.response.AuthResponseDto;
import com.smartmanage.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping()
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.authenticate(authRequestDto));
    }
}
