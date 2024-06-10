package bg.tu.varna.events.api.operations.personalevent.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePersonalEventResponse implements ProcessorResponse {
	private String message;

}
