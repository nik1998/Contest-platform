package com.application.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "organization", uniqueConstraints = {@UniqueConstraint(columnNames = "companyName")})
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;
    private String url;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    private Set<User> users = new HashSet<>();


    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }

    public Organization() {
    }
}
