package bg.tu.varna.events.api.operations.organization.patch;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.OrganizationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchOrganizationResponse implements ProcessorResponse {

	OrganizationModel organizationModel;
}

