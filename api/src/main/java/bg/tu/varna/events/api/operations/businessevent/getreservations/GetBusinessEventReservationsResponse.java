package bg.tu.varna.events.api.operations.businessevent.getreservations;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.ReservationModel;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBusinessEventReservationsResponse  implements ProcessorResponse {
	String eventName;
	List<ReservationModel> reservationModels;

}
