package com.jalbertolrz.employee_service.exceptions;



public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with Id: "+id+", not exist");
    }
}
