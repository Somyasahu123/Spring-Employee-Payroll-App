package com.bridgelabz.Spring_mysql.controller;

import com.bridgelabz.Spring_mysql.DTO.EmployeePayrollDTO;
import com.bridgelabz.Spring_mysql.model.Employee;
import com.bridgelabz.Spring_mysql.repository.EmployeeRepository;
import com.bridgelabz.Spring_mysql.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return service.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
    @PostMapping
    public EmployeePayrollDTO createEmployee(@RequestBody EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSalary());
        employeeRepository.save(employee);
        return employeeDTO;  // Returning DTO for API response
    }

    // Get All Employees as DTOs (GET)
    @GetMapping
    public List<EmployeePayrollDTO> getAllEmployeesdto() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeePayrollDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
    }

    // Get Employee by ID as DTO (GET)
    @GetMapping("/{id}")
    public EmployeePayrollDTO getEmployeeByIddto(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(emp -> new EmployeePayrollDTO(emp.getName(), emp.getSalary()))
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // Update Employee using DTO (PUT)
    @PutMapping("/{id}")
    public EmployeePayrollDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeePayrollDTO updatedDTO) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedDTO.getName());
                    employee.setSalary(updatedDTO.getSalary());
                    employeeRepository.save(employee);
                    return updatedDTO;
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    //List

    @GetMapping
    public List<Employee> getAllEmployeesList() {
        return service.getAllEmployeesList();
    }


    @GetMapping("/{id}")
    public Employee getEmployeeByIdList(@PathVariable int id) {
        return service.getEmployeeByIdList(id);
    }

    @PostMapping
    public Employee addEmployeeList(@RequestBody Employee employee) {
        return service.addEmployeeList(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeList(@PathVariable int id, @RequestBody Employee employee) {
        return service.updateEmployeeList(id, employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeList(@PathVariable int id) {
        return service.deleteEmployeeList(id) ? "Deleted Successfully" : "Employee Not Found";
    }

    
}
