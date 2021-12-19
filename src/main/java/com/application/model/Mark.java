package com.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "mark")
public class Mark {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer value;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User evaluator;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User gainer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Report report;

    public Mark() {

    }
}
