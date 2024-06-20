package com.api.springrest.vo.security;

import lombok.*;

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
