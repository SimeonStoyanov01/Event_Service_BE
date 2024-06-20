package bg.tu.varna.events.api.operations.reservation.patch;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchReservationRequest implements ProcessorRequest{
	@NotBlank(message = "Reservation id is required")
	private String reservationId;

	@Positive(message = "Must be a positive number")
	@NotNull(message = "You should provide a number of people for the event")
	private Integer numberOfPeople;

}