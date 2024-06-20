package bg.tu.varna.events.api.operations.organization.getaccumulated;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationAccumulatedEarningsRequest implements ProcessorRequest{
	@NotBlank(message = "Organization id is required")
	private String organizationId;
}