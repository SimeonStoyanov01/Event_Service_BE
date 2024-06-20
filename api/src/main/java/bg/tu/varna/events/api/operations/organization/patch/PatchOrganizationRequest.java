package bg.tu.varna.events.api.operations.organization.patch;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchOrganizationRequest implements ProcessorRequest {

	@NotBlank(message = "Organization id is required")
	private String organizationId;

	private String organizationName;

	private String organizationAddress;

	private Integer credibilityScore;
}