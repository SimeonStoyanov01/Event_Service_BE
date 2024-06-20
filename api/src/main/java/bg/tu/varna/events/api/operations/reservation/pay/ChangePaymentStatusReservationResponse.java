package bg.tu.varna.events.api.operations.reservation.pay;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePaymentStatusReservationResponse implements ProcessorResponse {
	private String message;
}

