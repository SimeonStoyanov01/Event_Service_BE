package bg.tu.varna.events.core.processors.user;

import bg.tu.varna.events.api.exceptions.InvalidRefreshTokenException;
import bg.tu.varna.events.api.exceptions.UserNotFoundException;
import bg.tu.varna.events.api.operations.user.refresh.RefreshOperation;
import bg.tu.varna.events.api.operations.user.refresh.RefreshRequest;
import bg.tu.varna.events.api.operations.user.refresh.RefreshResponse;
import bg.tu.varna.events.core.processors.external.JwtService;
import bg.tu.varna.events.persistence.entities.Token;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.TokenType;
import bg.tu.varna.events.persistence.repositories.TokenRepository;
import bg.tu.varna.events.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshProcessor implements RefreshOperation {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final TokenRepository tokenRepository;

	@Override
	public RefreshResponse process(RefreshRequest request) {
		String refreshToken = request.getRefreshToken();
		String userEmail = jwtService.extractUserEmail(refreshToken);

		User user = userRepository.findUserByEmail(userEmail)
				.orElseThrow(UserNotFoundException::new);

		if (!jwtService.isTokenValidForUser(refreshToken, user) || isRefreshTokenRevoked(refreshToken))
			throw new InvalidRefreshTokenException();

		String accessToken = jwtService.generateToken(user);
		revokeAllTokens(user);
		saveUserToken(user, accessToken,TokenType.BEARER);

		return RefreshResponse.builder()
				.jwtToken(accessToken)
				.refreshToken(refreshToken)
				.build();

	}

	private void revokeAllTokens(User user) {
		var validTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
		validTokens.stream()
				.filter(t -> t.getTokenType() == TokenType.BEARER)
				.forEach(token -> {
					token.setExpired(true);
					token.setRevoked(true);
				});
		tokenRepository.saveAll(validTokens);
	}

	private void saveUserToken(User user, String jwtToken,TokenType tokenType) {
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

	private boolean isRefreshTokenRevoked(String refreshToken) {
		var storedToken = tokenRepository.findByToken(refreshToken)
				.orElse(null);
		return storedToken == null || storedToken.getRevoked() || storedToken.getExpired();
	}
}
