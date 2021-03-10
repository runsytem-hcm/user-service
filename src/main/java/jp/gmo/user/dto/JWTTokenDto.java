package jp.gmo.user.dto;

import lombok.Data;

@Data
public class JWTTokenDto {
    private String token;
    private Long expirationDate;
}
