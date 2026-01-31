package com.jalbertolrz.employee_service.service;


import com.jalbertolrz.employee_service.clients.DepartmentClient;
import com.jalbertolrz.employee_service.model.Department;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DepartmentService {
    private final DepartmentClient departmentClient;


    @CircuitBreaker(name = "employee-service",fallbackMethod = "fallbackFindDepartmentById")
    @Retry(name = "employee-service")
    public Department findDepartmentById(Long id){
        return departmentClient.getDepartment(id);

    }

    private Department fallbackFindDepartmentById(Long id,Throwable ex){
        log.error("Department service is unavailable with id {}, with error {}",id,ex);
        return null;
    }
}
