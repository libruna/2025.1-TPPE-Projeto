package com.smartmanage.api.controller;

import com.smartmanage.api.dto.request.EmployeeRequestDto;
import com.smartmanage.api.dto.response.EmployeeResponseDto;
import com.smartmanage.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EmployeeResponseDto> saveEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return ResponseEntity.created(null).body(employeeService.saveEmployee(employeeRequestDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }
}
