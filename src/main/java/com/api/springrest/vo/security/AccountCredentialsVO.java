package com.api.springrest.vo.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor @Data
public class AccountCredentialsVO {

    private String username;
    private String password;
}
