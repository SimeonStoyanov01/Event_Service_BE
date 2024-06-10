package bg.tu.varna.events.api.operations.organization.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrganizationResponse implements ProcessorResponse {
	private String message;

}
