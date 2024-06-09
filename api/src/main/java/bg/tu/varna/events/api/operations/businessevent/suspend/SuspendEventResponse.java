package bg.tu.varna.events.api.operations.businessevent.suspend;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.EventModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspendEventResponse implements ProcessorResponse {
	EventModel eventModel;
}

