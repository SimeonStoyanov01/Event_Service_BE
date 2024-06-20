package bg.tu.varna.events.api.operations.businessevent.getmy;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.EventModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyEventsResponse implements ProcessorResponse {

	private List<EventModel> eventModels;
}

