package com.crit.oauthjwt2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class TokenDto {
    public String token;
    public Date tokenExpirationTime;
}
