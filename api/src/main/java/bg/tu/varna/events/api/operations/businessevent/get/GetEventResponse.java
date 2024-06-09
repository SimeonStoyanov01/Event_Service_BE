package bg.tu.varna.events.api.operations.businessevent.get;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.EventModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEventResponse implements ProcessorResponse {
	EventModel eventModel;
}

