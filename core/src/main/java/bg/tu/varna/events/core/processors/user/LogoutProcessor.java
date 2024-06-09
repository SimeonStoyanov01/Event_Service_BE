package bg.tu.varna.events.core.processors.user;

import bg.tu.varna.events.api.exceptions.TokenNotFoundException;
import bg.tu.varna.events.api.operations.user.logout.LogoutOperation;
import bg.tu.varna.events.persistence.entities.Token;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogoutProcessor implements LogoutOperation {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt)
				.orElseThrow(TokenNotFoundException::new);
		if (storedToken != null) {
			revokeAllTokens(storedToken.getUser());
			SecurityContextHolder.clearContext();
		}
	}

	private void revokeAllTokens(User user) {
		List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
		if (tokens.isEmpty())
			return;
		tokens.forEach(t -> {
			t.setExpired(true);
			t.setRevoked(true);
		});
		tokenRepository.saveAll(tokens);
	}
}
