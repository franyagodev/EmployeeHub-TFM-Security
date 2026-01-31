package com.jalbertolrz.employee_service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Department {
    private Long id;
    private String name;
    private City city;
}
