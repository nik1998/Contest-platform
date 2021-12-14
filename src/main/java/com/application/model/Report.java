package com.application.model;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Contest contest;

    private String text;

    private String fileName;

    private Integer rating = 0;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] fileReport;

    public void incRating(int delta) {
        this.rating += delta;
    }
}
