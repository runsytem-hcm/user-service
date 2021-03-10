package jp.gmo.user.dto.response;

import jp.gmo.user.dto.JWTTokenDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String jwt;
}
