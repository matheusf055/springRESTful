package com.api.springrest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

@JsonPropertyOrder({"key", "title", "author", "price", "launchDate"})
@NoArgsConstructor @AllArgsConstructor @Data
public class BookVO extends RepresentationModel<BookVO> {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String author;
    private Date launchDate;
    private BigDecimal price;
    private String title;
}
