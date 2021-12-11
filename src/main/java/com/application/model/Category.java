package com.application.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName="id", nullable=false)
    private Contest contest;

    public Category(){

    }

    public Category(String category, Contest contest){
        this.category = category;
        this.contest = contest;
    }
}
