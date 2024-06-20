package bg.tu.varna.events.api.operations.organization.get;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.OrganizationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationResponse implements ProcessorResponse {
	OrganizationModel organizationModel;
}

