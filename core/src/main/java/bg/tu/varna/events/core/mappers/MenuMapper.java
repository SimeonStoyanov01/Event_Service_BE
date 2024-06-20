package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.MenuModel;
import bg.tu.varna.events.persistence.entities.Menu;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper implements Converter<Menu, MenuModel> {
	@Override
	public MenuModel convert(Menu source) {
		return MenuModel
				.builder()
				.menuId(source.getMenuId())
				.menuName(source.getMenuName())
				.personalEventId(source.getPersonalEvent().getPersonalEventId())
				.build();
	}
}
