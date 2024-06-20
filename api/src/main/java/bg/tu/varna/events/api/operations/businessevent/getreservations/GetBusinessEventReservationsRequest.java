package bg.tu.varna.events.api.operations.businessevent.getreservations;

import bg.tu.varna.events.api.base.ProcessorRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBusinessEventReservationsRequest implements ProcessorRequest {
	private String eventId;

}
