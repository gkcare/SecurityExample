package com.gkcare.sec.service.impl;

import com.gkcare.sec.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECERET;

    private SecretKey secretKey;

    private static final long JWT_EXPIRATION_MS = 30 * 60 * 1000;

    @PostConstruct
    private void init(){
        byte[] keyBytes= Decoders.BASE64.decode(SECERET);
        secretKey= Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims,String username){
       return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
               .signWith(secretKey).compact();

    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiraion(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenValid(String token,String username){
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiraion(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver ) {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                // Use the latest parser method
                .verifyWith(secretKey) // Verify with the signing key
                .build() // Build the parser
                .parseSignedClaims(token) // Parse the signed claims
                .getPayload(); // Extract the payload
    }


}
