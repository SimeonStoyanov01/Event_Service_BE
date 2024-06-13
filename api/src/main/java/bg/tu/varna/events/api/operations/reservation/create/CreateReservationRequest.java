package bg.tu.varna.events.api.operations.reservation.create;

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
public class CreateReservationRequest implements ProcessorRequest{
	@NotBlank(message = "Event id is required")
	private String eventId;

	@Positive(message = "Capacity must be a positive number")
	@NotNull(message = "Capacity is required")
	private Integer numberOfPeople;
}