package com.example.crudangular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CredentialDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
}
