package com.smartmanage.api.service.impl;

import com.smartmanage.api.dto.request.EmployeeRequestDto;
import com.smartmanage.api.dto.response.EmployeeResponseDto;
import com.smartmanage.api.exception.BusinessException;
import com.smartmanage.api.model.entity.Employee;
import com.smartmanage.api.repository.EmployeeRepository;
import com.smartmanage.api.repository.PositionRepository;
import com.smartmanage.api.repository.RoleRepository;
import com.smartmanage.api.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository,
                               RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = new ModelMapper();
    }

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto) {
        employeeRepository.findByDocument(employeeRequestDto.getDocument())
                .ifPresent(employee -> {
                    throw new BusinessException("Funcionário já cadastrado.", HttpStatus.CONFLICT);
                });

        Employee employee = mapper.map(employeeRequestDto, Employee.class);
        employee.setPosition(positionRepository.getReferenceById(employeeRequestDto.getPosition().getId()));
        employee.setRole(roleRepository.getReferenceById(employeeRequestDto.getRole().getId()));
        employee.setPassword(passwordEncoder.encode(employeeRequestDto.getPassword()));

        return EmployeeResponseDto.builder()
                .id(employeeRepository.save(employee).getId())
                .build();
    }

    @Override
    public EmployeeResponseDto getEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Funcionário não encontrado.", HttpStatus.NOT_FOUND));

        return mapper.map(employee, EmployeeResponseDto.class);
    }
}
