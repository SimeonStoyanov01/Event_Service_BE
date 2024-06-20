package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
	private UUID eventId;

	private String eventName;

	private String eventDescription;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime eventDateTime;

	private Integer capacity;

	private UUID organizationId;

	private BigDecimal ticketPrice;

	private EventStatus status;

}
