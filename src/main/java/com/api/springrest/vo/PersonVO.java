package com.api.springrest.vo;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Data
public class PersonVO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
