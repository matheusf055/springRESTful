package com.api.springrest.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({"id", "firstName" , "LastName", "address", "gender"})
@NoArgsConstructor @AllArgsConstructor @Data
public class PersonVO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
