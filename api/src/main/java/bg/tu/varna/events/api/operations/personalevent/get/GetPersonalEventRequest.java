package bg.tu.varna.events.api.operations.personalevent.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonalEventRequest implements ProcessorRequest{

	@NotBlank(message = "Personal event id is required")
	private String personalEventId;
}