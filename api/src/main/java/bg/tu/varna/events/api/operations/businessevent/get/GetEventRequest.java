package bg.tu.varna.events.api.operations.businessevent.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEventRequest implements ProcessorRequest{

	@NotBlank(message = "Event id is required")
	private String eventId;
}