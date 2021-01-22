package ru.itis.resumeapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.resumeapi.models.User;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String getToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
