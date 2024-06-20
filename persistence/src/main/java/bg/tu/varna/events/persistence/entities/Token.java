package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String token;

	@Enumerated(EnumType.STRING)
	private TokenType tokenType;

	private Boolean revoked;

	private Boolean expired;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}