package com.jalbertolrz.employee_service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private Long id;
    private String name;
    private String country;
    private Integer countryCode;
    private Integer population;
}

