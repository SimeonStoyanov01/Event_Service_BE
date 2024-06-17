package bg.tu.varna.events.api.operations.menu.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMenuResponse implements ProcessorResponse {
	private String message;
}

