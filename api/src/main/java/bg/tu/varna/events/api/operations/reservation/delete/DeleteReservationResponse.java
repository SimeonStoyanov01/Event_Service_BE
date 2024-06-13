package bg.tu.varna.events.api.operations.reservation.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReservationResponse implements ProcessorResponse {
	private String message;


}
