package fr.eseo.equipe2.pglback.security;

import fr.eseo.equipe2.pglback.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
    @Value("${app.jwt.secret}") // You need to update the document "application.properties" WARNING!! some information are confidential
    private String SECRET_KEY;
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s", user.getLogin()))
                .setIssuer("ESEO")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
