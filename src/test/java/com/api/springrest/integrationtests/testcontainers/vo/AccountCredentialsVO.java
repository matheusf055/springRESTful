package com.api.springrest.integrationtests.testcontainers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor @Data
public class AccountCredentialsVO {

    private String username;
    private String password;
}
