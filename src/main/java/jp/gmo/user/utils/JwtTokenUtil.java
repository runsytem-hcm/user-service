package jp.gmo.user.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jp.gmo.user.dto.UserDetailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.base64-secret}")
    private String secret;

    @Value("${jwt.token-validity}")
    private long validity;

    public String doGenerateToken(UserDetailDto user, boolean rememberMe) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getAccountDto().getEmployeeName())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
