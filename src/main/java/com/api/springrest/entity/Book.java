package com.api.springrest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "books")
@NoArgsConstructor @AllArgsConstructor @Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author", columnDefinition = "longtext")
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lauch_date")
    private Date launchDate;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "title", nullable = false, length = 120)
    private String title;
}
