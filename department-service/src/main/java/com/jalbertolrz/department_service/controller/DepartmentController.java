package com.jalbertolrz.department_service.controller;

import com.jalbertolrz.department_service.clients.CityClient;
import com.jalbertolrz.department_service.clients.EmployeeClient;
import com.jalbertolrz.department_service.model.City;
import com.jalbertolrz.department_service.model.Department;
import com.jalbertolrz.department_service.service.CityService;
import com.jalbertolrz.department_service.service.DepartmentService;
import com.jalbertolrz.department_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.net.URI;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final CityService cityService;

    @GetMapping("/details")
    public ResponseEntity<List<Department>> findAllWithDetails(){
        List<Department> departments = departmentService.findAll();
        departments.forEach(department -> {
            // Llenar empleados
            department.setEmployees(employeeService.findAllByDepartmentById(department.getId()));
            // Llenar ciudad
            if(department.getCityId() != null) {
                department.setCity(cityService.findCityById(department.getCityId()));
            }
        });
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDepartmentById(@PathVariable Long id){
        return ResponseEntity.ok().body(departmentService.findByDepartmentId(id));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findDepartmentWithDetailsById(@PathVariable Long id){
       Department department=departmentService.findByDepartmentId(id);
        department.setEmployees(employeeService.findAllByDepartmentById(department.getId()));
        City city= cityService.findCityById(department.getId());
        department.setCity(city);

        return ResponseEntity.ok().body(department);
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department){
        Department dept=departmentService.createDepartment(department);
        URI location=URI.create("/api/v1/departments/"+dept.getId());
        return ResponseEntity.created(location).body(dept);
    }





}
