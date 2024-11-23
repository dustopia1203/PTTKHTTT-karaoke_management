package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee checkLogin(Employee employee);

}
