package com.jalbertolrz.department_service.service;


import com.jalbertolrz.department_service.clients.EmployeeClient;
import com.jalbertolrz.department_service.model.Employee;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeClient employeeClient;

    @CircuitBreaker(name = "department-service",fallbackMethod = "fallbackFindDepartmentId")
    @Retry(name = "department-service")
    public List<Employee> findAllByDepartmentById(Long id){
        return employeeClient.findAllByDepartmentId(id);
    }

    // Fallback que se ejecuta si el employee-service está caído
    public List<Employee> fallbackFindDepartmentId(Long id, Throwable ex) {
        System.out.println("Fallback ejecutado: employee-service no disponible. " +
                "Exception: " + ex.getMessage());
        return List.of(); // Devuelve lista vacía o datos por defecto
    }

}
