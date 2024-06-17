package com.api.springrest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"key", "firstName" , "LastName", "address", "gender"})
@NoArgsConstructor @AllArgsConstructor @Data
public class PersonVO extends RepresentationModel<PersonVO> {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
