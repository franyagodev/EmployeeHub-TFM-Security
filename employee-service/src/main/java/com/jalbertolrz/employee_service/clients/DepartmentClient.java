package com.jalbertolrz.employee_service.clients;

import com.jalbertolrz.employee_service.model.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service")
public interface DepartmentClient {

    @GetMapping("/api/v1/departments/{id}")
    Department getDepartment(@PathVariable("id")Long id);
}
