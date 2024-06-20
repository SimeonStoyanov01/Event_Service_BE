package bg.tu.varna.events.api.operations.menu.getall;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.MenuModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllMenusPerEventResponse implements ProcessorResponse {
	private List<MenuModel> menuModel;
}

