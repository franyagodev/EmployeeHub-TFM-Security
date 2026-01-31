package com.jalbertolrz.department_service.service;


import com.jalbertolrz.department_service.exception.DepartmentNotFoundException;
import com.jalbertolrz.department_service.model.City;
import com.jalbertolrz.department_service.model.Department;

import com.jalbertolrz.department_service.model.Employee;
import com.jalbertolrz.department_service.repository.DepartmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CityService cityService;
    private final EmployeeService employeeService;


    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    @CircuitBreaker(name = "department-service",fallbackMethod = "fallbackFindAll")
    public Department findByDepartmentId(Long id){
        return departmentRepository.findById(id).orElseThrow(()->new DepartmentNotFoundException(id));
    }

    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }


    @CircuitBreaker(name = "department-service",fallbackMethod = "fallbackFindDepartmentById")
    public Department findDepartmentWithCityAndEmployee(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        City city = cityService.findCityById(dept.getCityId());
        dept.setCity(city);
        List<Employee> employees= employeeService.findAllByDepartmentById(id);
        dept.setEmployees(employees);

        return dept;
    }

    private List<Department> fallbackFindAll(Throwable ex){
        System.out.println("Department service is unavailable, exception: "+ex.getMessage());
        return departmentRepository.findAll();
    }

    private Department fallbackFindDepartmentById(Long id, Throwable ex){
        System.out.println("Department with Id: "+id+", service is unavailable, exception: "+ex.getMessage());
        Department dept = departmentRepository.findById(id).orElse(null);
        if (dept != null) {
            dept.setEmployees(List.of()); // Sin empleados
        }
        return dept;
    }






}
