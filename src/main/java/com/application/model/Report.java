package com.application.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName="id", nullable=false)
    private User sender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName="id", nullable=false)
    private Contest contest;

    @Getter
    @Setter
    private String text;

}
