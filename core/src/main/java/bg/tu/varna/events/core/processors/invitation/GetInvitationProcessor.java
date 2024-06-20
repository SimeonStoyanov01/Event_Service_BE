package bg.tu.varna.events.core.processors.invitation;

import bg.tu.varna.events.api.exceptions.InvitationNotFoundException;
import bg.tu.varna.events.api.model.InvitationModel;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetInvitationProcessor implements GetInvitationOperation {
	private final InvitationRepository invitationRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;
	@Override
	public GetInvitationResponse process(GetInvitationRequest request) {
		Invitation invitation = invitationRepository.findById(UUID.fromString(request.getInvitationId()))
				.orElseThrow(InvitationNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,invitation.getPersonalEvent());

		InvitationModel invitationModel= conversionService.convert(invitation, InvitationModel.class);
		return GetInvitationResponse
				.builder()
				.invitationModel(invitationModel)
				.build();
	}
}
