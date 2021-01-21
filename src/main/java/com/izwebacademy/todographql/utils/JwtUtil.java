package com.izwebacademy.todographql.utils;

import com.izwebacademy.todographql.models.JwtUser;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.UserRepository;

import graphql.kickstart.servlet.context.GraphQLWebSocketContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtUtil {


	@Autowired
	private UserRepository userRepository;

	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // o

	public String generate(JwtUser jwtUser) {
		return Jwts.builder() // (1)
				.setSubject(jwtUser.getUsername()) // (2)
				.signWith(key) // (3)
				.compact();

	}

	public JwtUser validate(String token) {
		JwtUser jwtUser;

		try {
			Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			jwtUser = new JwtUser();
			jwtUser.setUsername(body.getSubject());
		} catch (JwtException e) {
			// don't trust the JWT!
			throw new EntityException(e.getLocalizedMessage(), token);
		}

		return jwtUser;
	}

	@NotNull
	public JwtUser getJwtUser() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			throw new GenericException("Authorization Error", null);
		}

		String token = authorization.replace("Bearer ", "");

		JwtUser jwtUser = this.validate(token);

		if (jwtUser == null) {
			throw new GenericException("Invalid Token", token);
		}
		return jwtUser;
	}

	public Long getUserId() {
		String username = this.getJwtUser().getUsername();
		Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(username);
		if (!dbUser.isPresent()) {
			throw new EntityException("User not found", username);
		}
		return dbUser.get().getId();
	}
}
