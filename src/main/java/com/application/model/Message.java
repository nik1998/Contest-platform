package com.application.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User sender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User receiver;

    public Message() {

    }

}
