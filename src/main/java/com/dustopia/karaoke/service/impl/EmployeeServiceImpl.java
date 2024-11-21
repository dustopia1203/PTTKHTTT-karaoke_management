package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.Employee;
import com.dustopia.karaoke.repository.EmployeeRepository;
import com.dustopia.karaoke.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean checkLogin(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(employee.getUsername());
        if (employeeOptional.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        if (!Objects.equals(employee.getPassword(), employeeOptional.get().getPassword())) {
            return false;
        }
        employee.setId(employeeOptional.get().getId());
        employee.setName(employeeOptional.get().getName());
        return true;
    }

}
