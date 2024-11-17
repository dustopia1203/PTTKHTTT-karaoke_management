package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
