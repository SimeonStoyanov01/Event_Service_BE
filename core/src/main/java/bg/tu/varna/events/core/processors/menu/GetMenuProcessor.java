package bg.tu.varna.events.core.processors.menu;

import bg.tu.varna.events.api.exceptions.MenuNotFoundException;
import bg.tu.varna.events.api.model.MenuModel;
import bg.tu.varna.events.api.operations.menu.get.GetMenuOperation;
import bg.tu.varna.events.api.operations.menu.get.GetMenuRequest;
import bg.tu.varna.events.api.operations.menu.get.GetMenuResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Menu;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMenuProcessor implements GetMenuOperation{
	private final MenuRepository menuRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;
	@Override
	public GetMenuResponse process(GetMenuRequest request) {
		Menu menu = menuRepository.findById(UUID.fromString(request.getMenuId()))
				.orElseThrow(MenuNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,menu.getPersonalEvent());

		MenuModel menuModel= conversionService.convert(menu, MenuModel.class);
		return GetMenuResponse
				.builder()
				.menuModel(menuModel)
				.build();
	}
}
