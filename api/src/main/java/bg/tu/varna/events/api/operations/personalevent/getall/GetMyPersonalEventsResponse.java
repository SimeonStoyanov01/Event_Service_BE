package bg.tu.varna.events.api.operations.personalevent.getall;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.PersonalEventModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyPersonalEventsResponse implements ProcessorResponse {

	private List<PersonalEventModel> eventModels;
}

