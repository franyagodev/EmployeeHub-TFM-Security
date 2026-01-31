package com.jalbertolrz.employee_service.service;

import com.jalbertolrz.employee_service.clients.DepartmentClient;
import com.jalbertolrz.employee_service.exceptions.EmployeeNotFoundException;
import com.jalbertolrz.employee_service.model.Department;
import com.jalbertolrz.employee_service.model.Employee;
import com.jalbertolrz.employee_service.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;


    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id){
     return employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    public List<Employee> findByDepartmentId(Long id){
        return employeeRepository.findByDepartmentId(id);
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @CircuitBreaker(name = "employee-service", fallbackMethod = "fallbackfindEmployeeWithDepartment")
    public Employee findEmployeeWithDepartment(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        Department department = departmentService.findDepartmentById(employee.getDepartmentId());
        employee.setDepartment(department);
        return employee;
    }

    @CircuitBreaker(name = "employee-service", fallbackMethod = "fallbackfindEmployeeWithDepartment")
    public List<Employee> findAllWithDepartment() {
        List<Employee> employees = employeeRepository.findAll();

        // Llenar el department usando Feign
        for (Employee e : employees) {
            try {
                Department dept = departmentService.findDepartmentById(e.getDepartmentId());
                e.setDepartment(dept);
            } catch (Exception ex) {
                e.setDepartment(null);
            }
        }

        return employees;
    }

    private Optional<Employee> fallbackfindEmployeeWithDepartment(Long id, Throwable ex){
        System.out.println("Employee is unavailable: "+ex.getMessage());
        return employeeRepository.findById(id);
    }

    private List<Employee> fallbackfindAllWithDepartment(Throwable ex){
        System.out.println("Employee is unavailable: "+ex.getMessage());
        return employeeRepository.findAll();
    }


}
