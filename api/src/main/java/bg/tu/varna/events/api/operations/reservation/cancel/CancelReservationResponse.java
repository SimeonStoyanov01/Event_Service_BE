package bg.tu.varna.events.api.operations.reservation.cancel;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.ReservationModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelReservationResponse implements ProcessorResponse {
	private ReservationModel reservationModel;
}

