package bg.tu.varna.events.core.processors.invitation;

import bg.tu.varna.events.api.exceptions.InvitationNotFoundException;
import bg.tu.varna.events.api.exceptions.MenuNotFoundException;
import bg.tu.varna.events.api.model.InvitationModel;
import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.Menu;
import bg.tu.varna.events.persistence.repositories.InvitationRepository;
import bg.tu.varna.events.persistence.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerInvitationProcessor implements AnswerInvitationOperation {

	private final InvitationRepository invitationRepository;
	private final MenuRepository menuRepository;
	private final ConversionService conversionService;
	private final PartialUpdateUtils partialUpdateUtils;
	private final ValidationUtils validationUtils;
	private static final Set<String> EXCLUDED_FIELDS = Set.of("invitationId", "inviteeEmail");

	@Override
	public AnswerInvitationResponse process(AnswerInvitationRequest request) {
		Invitation invitation = invitationRepository.findById(UUID.fromString(request.getInvitationId()))
				.orElseThrow(InvitationNotFoundException::new);

		Menu matchingMenu = menuRepository.findAllByPersonalEvent(invitation.getPersonalEvent())
				.stream()
				.filter(menu -> menu.getMenuName().equals(request.getMenuName()))
				.findFirst()
				.orElseThrow(MenuNotFoundException::new);

		invitation.setMenu(matchingMenu);

		validationUtils.validateInvitedGuestEmail(invitation, request.getInviteeEmail());

		partialUpdateUtils.updateFields(invitation, request, EXCLUDED_FIELDS);
		Invitation savedInvitation = invitationRepository.save(invitation);

		InvitationModel invitationModel = conversionService.convert(savedInvitation, InvitationModel.class);

		return AnswerInvitationResponse
				.builder()
				.invitationModel(invitationModel)
				.build();
	}
}
