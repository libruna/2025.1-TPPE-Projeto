package com.smartmanage.api.service;

import com.smartmanage.api.dto.request.EmployeeRequestDto;
import com.smartmanage.api.dto.response.EmployeeResponseDto;

import java.util.UUID;

public interface EmployeeService {

    EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto getEmployeeById(UUID id);
}
