package com.application.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User receiver;

    public Message() {

    }

}
