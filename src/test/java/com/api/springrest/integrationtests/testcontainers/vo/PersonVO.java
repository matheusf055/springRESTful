package com.api.springrest.integrationtests.testcontainers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class PersonVO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
