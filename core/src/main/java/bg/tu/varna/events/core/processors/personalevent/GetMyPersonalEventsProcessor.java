package bg.tu.varna.events.core.processors.personalevent;

import bg.tu.varna.events.api.exceptions.EmptyEventsListException;
import bg.tu.varna.events.api.model.PersonalEventModel;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsOperation;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsRequest;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyPersonalEventsProcessor implements GetMyPersonalEventsOperation {
	private final PersonalEventRepository personalEventRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;
	@Override
	public GetMyPersonalEventsResponse process(GetMyPersonalEventsRequest request) {
		User user = validationUtils.getCurrentAuthenticatedUser();

		List<PersonalEventModel> personalEventModels = personalEventRepository.findAllByUser(user)
				.stream()
				.map(event -> conversionService.convert(event, PersonalEventModel.class))
				.toList();

		if (personalEventModels.isEmpty()) {
			throw new EmptyEventsListException();
		}
		return GetMyPersonalEventsResponse
				.builder()
				.eventModels(personalEventModels)
				.build();
	}
}
