package com.example.demo.jwt;

import java.security.SignatureException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access.token.expiration}")
	private long accessTokenExpirationTime;

	@Value("${jwt.refresh.token.expiration}")
	private long refreshTokenExpirationTime;

	public String generateAccessToken(String userId, Role role) throws SignatureException {
		logger.info("Generating access token for user: " + userId);
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		Instant now = Instant.now();
		Instant expiration = now.plus(Duration.ofMillis(accessTokenExpirationTime));

		return Jwts.builder().setSubject(userId).claim("role", role.toString()).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiration)).signWith(key).compact();
	}

//	public String generateRefreshToken(String oldJwtToken) {
//		logger.info("Generating refresh token");
//		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
//		boolean validateToken = validateToken(oldJwtToken);
//		if (validateToken) {
//			Instant now = Instant.now();
//			Instant expiration = now.plus(Duration.ofMillis(refreshTokenExpirationTime));
//			return Jwts.builder().setIssuedAt(Date.from(now)).setExpiration(Date.from(expiration)).signWith(key)
//					.compact();
//		} else {
//			logger.error("Invalid JWT token provided.");
//			throw new IllegalArgumentException("Invalid JWT token provided.");
//		}
//	}

	public Map<String, String> validateToken(String token) {
		logger.info("Validating token");
		Map<String, String> result = new HashMap<>();
		try {
			SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			Instant expiration = claims.getExpiration().toInstant();
			Object object = claims.get("role");
			if (Instant.now().isAfter(expiration)) {
				logger.warn("Token has expired");
				result.put("error", "Token has expired");
				return result;
			}
			String subject = claims.getSubject();
			String role = (String) object;
			logger.info("Token validation successful for subject: {}", subject);
			result.put("id", subject);
			result.put("role", role);
			return result;
		} catch (ExpiredJwtException ex) {
			logger.warn("Token has expired");
			result.put("error", "Token has expired");
			return result;
		} catch (JwtException ex) {
			logger.error("Error occurred while validating token: {}", ex.getMessage());
			result.put("error", "Invalid token");
			return result;
		}
	}

	public boolean validateSecretKey(String secretKey) {
		return secret.equals(secretKey);
	}

}
