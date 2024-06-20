package bg.tu.varna.events.api.operations.reservation.pay;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePaymentStatusReservationRequest implements ProcessorRequest{

	@NotBlank(message = "Reservation id is required")
	private String reservationId;
}