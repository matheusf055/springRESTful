package com.api.springrest.integrationtests.testcontainers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Data
public class TokenVO {

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accesToken;
    private String refreshToken;
}
