package com.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mark")
public class Mark {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private Integer value;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User evaluator;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User gainer;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Report report;

    public Mark() {

    }
}
