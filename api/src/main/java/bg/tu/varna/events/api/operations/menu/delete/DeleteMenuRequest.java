package bg.tu.varna.events.api.operations.menu.delete;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMenuRequest implements ProcessorRequest{

	@NotBlank(message = "Menu id is required")
	private String menuId;

}