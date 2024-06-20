package bg.tu.varna.events.api.operations.organization.getearnings;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationEarningsResponse implements ProcessorResponse {

	private List<GetEventEarningsResponse> organizationEarningsPerEvent;
}

