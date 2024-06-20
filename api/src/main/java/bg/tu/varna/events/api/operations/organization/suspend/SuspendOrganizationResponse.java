package bg.tu.varna.events.api.operations.organization.suspend;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.OrganizationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspendOrganizationResponse implements ProcessorResponse {
	private OrganizationModel organizationModel;
}

