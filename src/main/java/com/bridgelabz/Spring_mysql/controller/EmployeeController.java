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

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }


    @GetMapping("/by-id/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }


    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return service.addEmployee(employee);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }

    @PostMapping("/dto/add")
    public EmployeePayrollDTO createEmployee(@RequestBody EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSalary());
        employeeRepository.save(employee);
        return employeeDTO;
    }

    @GetMapping("/dto/all")
    public List<EmployeePayrollDTO> getAllEmployeesDTO() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeePayrollDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
    }

    /*** ---- GET EMPLOYEE BY ID AS DTO ---- ***/
    @GetMapping("/dto/by-id/{id}")
    public EmployeePayrollDTO getEmployeeByIdDTO(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(emp -> new EmployeePayrollDTO(emp.getName(), emp.getSalary()))
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    /*** ---- UPDATE EMPLOYEE USING DTO ---- ***/
    @PutMapping("/dto/update/{id}")
    public EmployeePayrollDTO updateEmployeeDTO(@PathVariable Long id, @RequestBody EmployeePayrollDTO updatedDTO) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedDTO.getName());
                    employee.setSalary(updatedDTO.getSalary());
                    employeeRepository.save(employee);
                    return updatedDTO;
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @GetMapping("/list/all")
    public List<Employee> getAllEmployeesList() {
        return service.getAllEmployeesList();
    }

    @GetMapping("/list/by-id/{id}")
    public Employee getEmployeeByIdList(@PathVariable int id) {
        return service.getEmployeeByIdList(id);
    }

    /*** ---- ADD EMPLOYEE TO LIST ---- ***/
    @PostMapping("/list/add")
    public Employee addEmployeeList(@RequestBody Employee employee) {
        return service.addEmployeeList(employee);
    }

    @PutMapping("/list/update/{id}")
    public Employee updateEmployeeList(@PathVariable int id, @RequestBody Employee employee) {
        return service.updateEmployeeList(id, employee);
    }

    @DeleteMapping("/list/delete/{id}")
    public String deleteEmployeeList(@PathVariable int id) {
        return service.deleteEmployeeList(id) ? "Deleted Successfully" : "Employee Not Found";
    }
}
