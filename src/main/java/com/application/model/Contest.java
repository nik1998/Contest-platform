package com.application.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contest")
public class Contest {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = true)
    private Organization organization;

    @OneToMany(mappedBy = "contest")
    private List<Category> categories = new ArrayList<>();

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String contestName;

    @Getter
    @Setter
    private Date deadline;

    @Getter
    @Setter
    private Date startDate;

    @Getter
    @Setter
    private Integer prize;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "jury",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "con_id", referencedColumnName = "id"))
    private List<User> jury = new ArrayList<>();

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

    public List<User> getJury() {
        return jury;
    }

    public void setJury(List<User> jury) {
        this.jury = jury;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategory(List<Category> categories) {
        this.categories = categories;
    }
}
