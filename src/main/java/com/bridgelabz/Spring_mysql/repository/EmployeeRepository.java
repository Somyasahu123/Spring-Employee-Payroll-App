package com.bridgelabz.Spring_mysql.repository;


import com.bridgelabz.Spring_mysql.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
