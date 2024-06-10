package bg.tu.varna.events.api.operations.personalevent.create;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.PersonalEventModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonalEventResponse implements ProcessorResponse {
	PersonalEventModel eventModel;
}

