package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModel {
	private UUID organizationId;
	private String organizationName;
	private String organizationAddress;
	private Integer credibilityScore;
	private OrganizationStatus organizationStatus;
}