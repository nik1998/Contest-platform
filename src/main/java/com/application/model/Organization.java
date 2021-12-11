package com.application.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "org_users",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "org_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    public Organization() {
    }
}
