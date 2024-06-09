package bg.tu.varna.events.api.operations.businessevent.create;

import bg.tu.varna.events.api.base.ProcessorRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest implements ProcessorRequest{

	@NotBlank(message = "Event name is required")
	private String eventName;

	@NotBlank(message = "Event description is required")
	private String eventDescription;

	@NotNull(message = "Event date and time is required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Future(message = "Event date and time must be in the future")
	private LocalDateTime eventDateTime;

//	@NotNull(message = "User ID is required")
//	private UUID userId;

	@Positive(message = "Capacity must be a positive number")
	@NotNull(message = "Capacity is required")
	private Integer capacity;

	@NotNull(message = "Organization ID is required")
	private UUID organizationId;
}