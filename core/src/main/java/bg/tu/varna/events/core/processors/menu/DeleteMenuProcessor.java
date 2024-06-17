package bg.tu.varna.events.core.processors.menu;

import bg.tu.varna.events.api.exceptions.MenuNotFoundException;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuOperation;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuRequest;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Menu;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteMenuProcessor implements DeleteMenuOperation {
	private final MenuRepository menuRepository;
	private final ValidationUtils validationUtils;

	@Override
	public DeleteMenuResponse process(DeleteMenuRequest request) {
		Menu menu = menuRepository.findById(UUID.fromString(request.getMenuId()))
				.orElseThrow(MenuNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,menu.getPersonalEvent());

		menuRepository.delete(menu);

		return DeleteMenuResponse
				.builder()
				.message("Event deleted successfully")
				.build();
	}
}
