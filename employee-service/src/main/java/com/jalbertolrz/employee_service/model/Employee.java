package com.jalbertolrz.employee_service.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Department department;
    private Long departmentId;
    private String name;
    private String email;
    private Integer birthdayYear;
    private String position;

}
