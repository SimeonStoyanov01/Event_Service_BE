package bg.tu.varna.events.core.processors.menu;

import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.MenuModel;
import bg.tu.varna.events.api.operations.menu.create.CreateMenuOperation;
import bg.tu.varna.events.api.operations.menu.create.CreateMenuRequest;
import bg.tu.varna.events.api.operations.menu.create.CreateMenuResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Menu;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.repositories.MenuRepository;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuProcessor implements CreateMenuOperation {
	private final PersonalEventRepository personalEventRepository;
	private final MenuRepository menuRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;

	@Override
	public CreateMenuResponse process(CreateMenuRequest request) {
		PersonalEvent personalEvent = personalEventRepository.findById(request.getPersonalEventId())
				.orElseThrow(PersonalEventNotFoundException::new);

		validationUtils.validateUserPersonalEvent(validationUtils.getCurrentAuthenticatedUser(), personalEvent);

		Menu menu = saveMenu(request, personalEvent);

		MenuModel menuModel = conversionService.convert(menu, MenuModel.class);

		return CreateMenuResponse
				.builder()
				.menuModel(menuModel)
				.build();
	}

	private Menu saveMenu(CreateMenuRequest request, PersonalEvent personalEvent) {
		Menu menu = Menu.builder()
				.menuName(request.getMenuName())
				.personalEvent(personalEvent)
				.build();
		return menuRepository.save(menu);
	}
}
