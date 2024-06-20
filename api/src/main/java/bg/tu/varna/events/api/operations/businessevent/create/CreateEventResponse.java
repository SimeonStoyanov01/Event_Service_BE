package bg.tu.varna.events.api.operations.businessevent.create;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.EventModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventResponse implements ProcessorResponse {
	EventModel eventModel;
}

