package bg.tu.varna.events.api.operations.personalevent.create;

import bg.tu.varna.events.api.base.ProcessorRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonalEventRequest implements ProcessorRequest{

	@NotBlank(message = "Event name is required")
	private String eventName;

	@NotBlank(message = "Event description is required")
	private String eventDescription;

	@NotBlank(message = "Event location is required")
	private String eventLocation;

	@NotNull(message = "Event date and time is required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Future(message = "Event date and time must be in the future")
	private LocalDateTime eventDateTime;

//	@NotNull(message = "User ID is required")
//	private UUID userId;


}