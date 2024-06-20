package bg.tu.varna.events.api.operations.menu.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMenuRequest implements ProcessorRequest{

	@NotBlank(message = "Menu id is required")
	private String menuId;

}