package bg.tu.varna.events.api.operations.businessevent.patch;

import bg.tu.varna.events.api.base.ProcessorRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchEventRequest implements ProcessorRequest{
	@NotBlank(message = "Event name is required")
	private String eventId;

	private String eventName;

	private String eventDescription;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Future(message = "Event date and time must be in the future")
	private LocalDateTime eventDateTime;

	@Positive(message = "Capacity must be a positive number")
	private Integer capacity;

//	private EventStatus status;

}