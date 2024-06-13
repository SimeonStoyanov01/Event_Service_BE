package bg.tu.varna.events.api.operations.reservation.getmy;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.ReservationModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyReservationsResponse implements ProcessorResponse {

	private List<ReservationModel> reservationModels;
}

