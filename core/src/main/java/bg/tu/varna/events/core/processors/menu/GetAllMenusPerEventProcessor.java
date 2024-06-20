package bg.tu.varna.events.core.processors.menu;

import bg.tu.varna.events.api.exceptions.EmptyMenuListException;
import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.MenuModel;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventOperation;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventRequest;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.MenuRepository;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllMenusPerEventProcessor implements GetAllMenusPerEventOperation {
	private final MenuRepository menuRepository;
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetAllMenusPerEventResponse process(GetAllMenusPerEventRequest request) {
		PersonalEvent personalEvent = personalEventRepository.findById(UUID.fromString(request.getPersonalEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,personalEvent);

		List<MenuModel> menuModels = menuRepository.findAllByPersonalEvent(personalEvent)
				.stream()
				.map(menu -> conversionService.convert(menu, MenuModel.class))
				.toList();

		if (menuModels.isEmpty()) {
			throw new EmptyMenuListException();
		}
		return GetAllMenusPerEventResponse
				.builder()
				.menuModel(menuModels)
				.build();
	}
}
