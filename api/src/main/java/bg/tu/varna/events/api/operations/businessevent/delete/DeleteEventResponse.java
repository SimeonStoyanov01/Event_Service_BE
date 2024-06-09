package bg.tu.varna.events.api.operations.businessevent.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEventResponse implements ProcessorResponse {
	private String message;

}
