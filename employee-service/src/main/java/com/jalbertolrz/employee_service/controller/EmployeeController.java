package com.jalbertolrz.employee_service.controller;


import com.jalbertolrz.employee_service.model.Department;
import com.jalbertolrz.employee_service.model.Employee;
import com.jalbertolrz.employee_service.service.DepartmentService;
import com.jalbertolrz.employee_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;


    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok().body(employeeService.findEmployeeById(id));
    }

    @GetMapping("/details")
    public ResponseEntity<?> findEmployeeWithDetails(){
        return ResponseEntity.ok().body(employeeService.findAllWithDepartment());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findEmployeeWithDetailsById(@PathVariable Long id){
        return ResponseEntity.ok().body(employeeService.findEmployeeWithDepartment(id));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> findDepartmentById(@PathVariable Long id){
        return ResponseEntity.ok().body(employeeService.findByDepartmentId(id));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        Employee e=employeeService.createEmployee(employee);
        URI location = URI.create("/api/v1/employees/" + e.getId());
        return ResponseEntity.created(location).body(e);
    }







}
