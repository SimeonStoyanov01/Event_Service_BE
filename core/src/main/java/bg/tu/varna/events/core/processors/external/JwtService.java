package bg.tu.varna.events.core.processors.external;

import bg.tu.varna.events.persistence.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class  JwtService {

	private final TokenRepository tokenRepository;

	@Value("${application.security.jwt.secret-key}")
	private String SECRET_KEY;

	@Value("${application.security.jwt.expiration}")
	private Long JWT_EXPIRATION;

	@Value("${application.security.jwt.refresh-token.expiration}")
	@Getter
	private Long REFRESH_EXPIRATION;

	public String extractUserEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}


	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
	) {
		return buildToken(extraClaims, userDetails, JWT_EXPIRATION);
	}
	public String generateRefreshToken(
			UserDetails userDetails
	) {
		return buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expiration) {
		return Jwts
				.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}

	public boolean isTokenValidForUser(String token, UserDetails userDetails) {
		final String userName = extractUserEmail(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token).getPayload();
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isTokenValidInTokenRepo(String jwt) {
		return tokenRepository.findByToken(jwt)
				.map(t -> !t.getExpired() && !t.getRevoked())
				.orElse(false);
	}
}
