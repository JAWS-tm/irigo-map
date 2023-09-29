package fr.eseo.equipe2.pglback.security;

import fr.eseo.equipe2.pglback.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    // to long delay is a bad practice (use refresh token for renew access token regularly)
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    @Value("${app.jwt.secret}")
    // You need to update the document "application.properties" WARNING!! some information are confidential
    private String SECRET_KEY;


    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateAccessToken(User user) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("IrigoMap")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(secret, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);

            return !isTokenExpired(token);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }


}
