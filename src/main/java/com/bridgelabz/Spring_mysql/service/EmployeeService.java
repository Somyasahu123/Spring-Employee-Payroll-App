package com.bridgelabz.Spring_mysql.service;


import com.bridgelabz.Spring_mysql.model.Employee;
import com.bridgelabz.Spring_mysql.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (repository.existsById(id)) {
            updatedEmployee.setId(id);
            return repository.save(updatedEmployee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
    private final List<Employee> employees = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Employee> getAllEmployeesList() {
        return employees;
    }

    public Employee getEmployeeByIdList(int id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee addEmployeeList(Employee employee) {
        employee.setId(idCounter++);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployeeList(int id, Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                updatedEmployee.setId((long)id);
                employees.set(i, updatedEmployee);
                return updatedEmployee;
            }
        }
        return null;
    }

    public boolean deleteEmployeeList(int id) {
        return employees.removeIf(emp -> emp.getId() == id);
    }
}


