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
    public Employee checkLogin(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(employee.getUsername());
        if (employeeOptional.isEmpty() || !Objects.equals(employee.getPassword(), employeeOptional.get().getPassword())) {
            return null;
        }
        return employeeOptional.get();
    }

}
