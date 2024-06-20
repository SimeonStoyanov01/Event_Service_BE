package bg.tu.varna.events.core.processors.invitation;

import bg.tu.varna.events.api.exceptions.InvitationNotFoundException;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteInvitationProcessor implements DeleteInvitationOperation {
	private final InvitationRepository invitationRepository;
	private final ValidationUtils validationUtils;

	@Override
	public DeleteInvitationResponse process(DeleteInvitationRequest request) {
		Invitation invitation = invitationRepository.findById(UUID.fromString(request.getInvitationId()))
				.orElseThrow(InvitationNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,invitation.getPersonalEvent());

		invitationRepository.delete(invitation);

		return DeleteInvitationResponse
				.builder()
				.message("Event deleted successfully")
				.build();
	}
}
