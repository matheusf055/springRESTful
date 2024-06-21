package com.api.springrest.integrationtests.testcontainers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Data
public class BookVO {

    private Long id;
    private String author;
    private Date launchDate;
    private BigDecimal price;
    private String title;
}
