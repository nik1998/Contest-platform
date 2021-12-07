package com.application.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "organization", uniqueConstraints = {@UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "companyName")})
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;
    private String email;
    private String password;
    private String url;
    private String description;

    public Organization() {
    }
}
