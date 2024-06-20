package bg.tu.varna.events.api.operations.personalevent.patch;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.PersonalEventModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchPersonalEventResponse implements ProcessorResponse {
	PersonalEventModel eventModel;
}

