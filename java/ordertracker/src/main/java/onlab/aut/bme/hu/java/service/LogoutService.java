package onlab.aut.bme.hu.java.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Token;
import onlab.aut.bme.hu.java.repository.TokenRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		final String jwt;
		jwt = authHeader.substring(7);

		Token storedToken = tokenRepository.findByToken(jwt)
			.orElse(null);
		if (storedToken == null) {
			return;
		}
		storedToken.setExpired(true);
		storedToken.setRevoked(true);
		tokenRepository.save(storedToken);
		SecurityContextHolder.clearContext();
	}
}