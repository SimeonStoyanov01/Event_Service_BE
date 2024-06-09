package bg.tu.varna.events.core.processors.user;

import bg.tu.varna.events.api.exceptions.UserNotFoundException;
import bg.tu.varna.events.api.operations.user.login.AuthenticationOperation;
import bg.tu.varna.events.api.operations.user.login.AuthenticationRequest;
import bg.tu.varna.events.api.operations.user.login.AuthenticationResponse;
import bg.tu.varna.events.core.processors.external.JwtService;
import bg.tu.varna.events.persistence.entities.Token;
import bg.tu.varna.events.persistence.entities.TokenType;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.TokenRepository;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationProcessor implements AuthenticationOperation {

	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public AuthenticationResponse process(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
		));
		User user = userRepository.findUserByEmail(request.getEmail())
				.orElseThrow(UserNotFoundException::new);

		String jwtToken = jwtService.generateToken(user);
		String refreshToken = findValidRefreshTokenForUser(user);
		revokeAccessTokens(user);
		saveUserToken(user, jwtToken, TokenType.BEARER);
		return AuthenticationResponse
				.builder()
				.jwtToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}

	private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
		Token token = Token
				.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(tokenType)
				.revoked(false)
				.expired(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAccessTokens(User user) {
		List<Token> token = tokenRepository.findAllValidTokenByUser(user.getUserId());
		if (token.isEmpty())
			return;
		token.stream()
				.filter(t -> t.getTokenType() == TokenType.BEARER)
				.forEach(t -> {
					t.setExpired(true);
					t.setRevoked(true);
				});
		tokenRepository.saveAll(token);
	}

	private String findValidRefreshTokenForUser(User user) {
		return tokenRepository.findAllValidTokenByUser(user.getUserId()).stream()
				.filter(token -> token.getTokenType() == TokenType.REFRESH)
				.filter(token -> !token.getExpired() && !token.getRevoked())
				.map(Token::getToken)
				.findFirst()
				.orElseGet(() -> {
					String newRefreshToken = jwtService.generateRefreshToken(user);
					saveUserToken(user, newRefreshToken, TokenType.REFRESH);
					return newRefreshToken;
				});
	}
}
