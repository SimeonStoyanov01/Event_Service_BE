package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID organizationId;

	@Column(name = "organization_name", unique = true, nullable = false)
	private String organizationName;

	@Column(name = "organization_address")
	private String organizationAddress;

	private Integer credibilityScore;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrganizationStatus organizationStatus;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private List<User> businessUsers = new ArrayList<>();

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private List<Event> events = new ArrayList<>();

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private List<Subscription> subscriptions = new ArrayList<>();
}
