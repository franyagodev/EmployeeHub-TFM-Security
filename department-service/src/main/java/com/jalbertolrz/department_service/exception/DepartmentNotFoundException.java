package com.jalbertolrz.department_service.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department with Id: "+id+", not exist");
    }
}
