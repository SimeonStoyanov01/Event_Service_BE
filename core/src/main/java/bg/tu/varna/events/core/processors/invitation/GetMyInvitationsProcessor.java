package bg.tu.varna.events.core.processors.invitation;

import bg.tu.varna.events.api.exceptions.EmptyInvitationsListException;
import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.InvitationModel;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsOperation;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsRequest;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.InvitationRepository;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMyInvitationsProcessor implements GetMyInvitationsOperation {
	private final InvitationRepository invitationRepository;
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetMyInvitationsResponse process(GetMyInvitationsRequest request) {
		PersonalEvent personalEvent = personalEventRepository.findById(UUID.fromString(request.getPersonalEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,personalEvent);

		List<InvitationModel> invitationModels = invitationRepository.findAllByPersonalEvent(personalEvent)
				.stream()
				.map(invitation -> conversionService.convert(invitation, InvitationModel.class))
				.toList();

		if (invitationModels.isEmpty()) {
			throw new EmptyInvitationsListException();
		}
		return GetMyInvitationsResponse
				.builder()
				.invitationModel(invitationModels)
				.build();
	}
}
