package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationModel {
	private UUID reservationId;

	private UUID userId;

	private UUID eventId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime reservationDateTime;

	private Integer numberOfPeople;

	private ReservationStatus reservationStatus;
}