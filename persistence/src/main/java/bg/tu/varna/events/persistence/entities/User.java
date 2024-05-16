package bg.tu.varna.events.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@ManyToOne
	@JoinColumn(name = "organization_id", nullable = true)
	private Organization organization;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Reservation> reservations = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<PersonalEvent> personalEvents = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Subscription> subscriptions = new HashSet<>();
}