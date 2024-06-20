package bg.tu.varna.events.api.operations.menu.get;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.MenuModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMenuResponse implements ProcessorResponse {
	private MenuModel menuModel;
}

