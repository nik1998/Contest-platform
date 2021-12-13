package com.application.model;


import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "contest")
@Data
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = true)
    private Organization organization;

    private String categories;
    private String description;
    private String contestName;
    private Date deadline;
    private Date startDate;
    private Integer prize = 0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "jury",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "con_id", referencedColumnName = "id"))
    private Set<User> jury = new HashSet<>();


    @ManyToMany(mappedBy = "contests")
    private Set<User> users=new HashSet<>();

    public Contest() {

    }

    public Contest(String contestName, String description, Date startDate, Date deadline, Integer prize, Organization organization) {
        this.contestName = contestName;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.prize = prize;
        this.organization = organization;
    }

    public void addJury(User user){
        this.jury.add(user);
    }
}
