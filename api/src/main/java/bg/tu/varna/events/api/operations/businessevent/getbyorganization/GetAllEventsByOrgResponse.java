package bg.tu.varna.events.api.operations.businessevent.getbyorganization;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.EventModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllEventsByOrgResponse implements ProcessorResponse {

	private List<EventModel> eventModels;
}

