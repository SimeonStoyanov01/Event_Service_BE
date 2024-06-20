package bg.tu.varna.events.api.operations.businessevent.getearnings;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEventEarningsResponse implements ProcessorResponse {
	private String eventName;
	private BigDecimal estimatedEarnings;
	private BigDecimal actualEarnings;
}

