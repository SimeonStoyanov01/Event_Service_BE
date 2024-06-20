package bg.tu.varna.events.api.operations.menu.create;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuRequest implements ProcessorRequest {
	@NotBlank(message = "Menu name is required")
	private String menuName;

	@NotNull(message = "Personal event ID is required")
	private UUID personalEventId;
}