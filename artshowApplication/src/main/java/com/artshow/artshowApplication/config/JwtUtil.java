package com.artshow.artshowApplication.config;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import com.artshow.artshowApplication.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

//    @Value("${jwt.secret}")
    private String secretKey ;

    @Value("${jwt.expiration}")
    private long expiration;
    public JwtUtil() {
		secretKey = generateSecretKey();
	}
    
    public String generateSecretKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGen.generateKey();
			System.out.println("Secret Key : "+ secretKey.toString());
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating secret key", e);
		}
	}
    private Key getKey() {
		// TODO Auto-generated method stub
//    	secretKey= Base64.getEncoder().encodeToString(secretKey);
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//		System.out.println(Keys.hmacShaKeyFor(keyBytes));
		return Keys.hmacShaKeyFor(keyBytes);
	}

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", ((User) userDetails).getAuthorities().iterator().next().getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
        
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
    	
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}

