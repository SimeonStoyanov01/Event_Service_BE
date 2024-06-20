package bg.tu.varna.events.api.operations.organization.getaccumulated;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationAccumulatedEarningsResponse implements ProcessorResponse {

	private BigDecimal accumulatedEarnings;
}

