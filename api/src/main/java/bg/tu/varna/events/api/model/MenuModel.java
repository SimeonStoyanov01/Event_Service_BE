package bg.tu.varna.events.api.model;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuModel {
	private UUID menuId;
	private String menuName;
	private UUID personalEventId;
}
