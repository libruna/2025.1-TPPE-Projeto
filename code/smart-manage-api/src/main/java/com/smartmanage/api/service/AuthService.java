package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.AuthRequestDto;
import com.smartmanage.api.dto.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto authenticate(AuthRequestDto authRequestDto);
}
