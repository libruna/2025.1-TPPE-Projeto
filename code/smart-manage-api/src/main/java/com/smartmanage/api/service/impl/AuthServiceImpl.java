package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.AuthRequestDto;
import com.smartmanage.api.dto.response.AuthResponseDto;
import com.smartmanage.api.model.entity.Employee;
import com.smartmanage.api.repository.EmployeeRepository;
import com.smartmanage.api.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${security.jwt.expires-in}")
    private long DEFAULT_TOKEN_TTL_SECONDS;

    public AuthServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        Optional<Employee> employeeOptional = employeeRepository.findByDocument(authRequestDto.getDocument());

        if (employeeOptional.isEmpty() || !passwordEncoder.matches(authRequestDto.getPassword(), employeeOptional.get().getPassword())) {
            throw new BadCredentialsException("CPF ou senha inválidos.");
        }

        Employee employee = employeeOptional.get();

        Instant now = Instant.now();
        long expiresIn = DEFAULT_TOKEN_TTL_SECONDS;

        String scope = employee.getRole().getName();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("smart-manage-api")
                .subject(employee.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(DEFAULT_TOKEN_TTL_SECONDS))
                .claim("scope", scope)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponseDto(jwtValue, expiresIn);
    }
}
