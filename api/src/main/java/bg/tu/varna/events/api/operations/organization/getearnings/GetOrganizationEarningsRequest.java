package bg.tu.varna.events.api.operations.organization.getearnings;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationEarningsRequest implements ProcessorRequest{
	@NotBlank(message = "Organization id is required")
	private String organizationId;
}