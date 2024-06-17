package bg.tu.varna.events.api.operations.menu.create;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.MenuModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuResponse implements ProcessorResponse {
	private MenuModel menuModel;
}

