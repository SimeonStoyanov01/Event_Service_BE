package bg.tu.varna.events.api.operations.personalevent.patch;

import bg.tu.varna.events.api.base.ProcessorRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchPersonalEventRequest implements ProcessorRequest{
	@NotBlank(message = "Personal event id is required")
	private String eventId;

	private String eventName;

	private String eventDescription;

	private String eventLocation;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Future(message = "Event date and time must be in the future")
	private LocalDateTime eventDateTime;

}