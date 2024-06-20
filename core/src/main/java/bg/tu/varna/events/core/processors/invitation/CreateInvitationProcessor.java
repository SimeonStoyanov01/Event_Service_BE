package bg.tu.varna.events.core.processors.invitation;

import bg.tu.varna.events.api.exceptions.PersonalEventNotFoundException;
import bg.tu.varna.events.api.model.InvitationModel;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationResponse;
import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.InvitationStatus;
import bg.tu.varna.events.persistence.repositories.InvitationRepository;
import bg.tu.varna.events.persistence.repositories.PersonalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateInvitationProcessor implements CreateInvitationOperation {
	private final PersonalEventRepository personalEventRepository;
	private final ValidationUtils validationUtils;
	private final InvitationRepository invitationRepository;
	private final ConversionService conversionService;
	private final MailerOperation mailerOperation;
	@Value("${client.url}")
	String clientUrl;
	@Override
	public CreateInvitationResponse process(CreateInvitationRequest request) {
		PersonalEvent personalEvent = personalEventRepository.findById(UUID.fromString(request.getPersonalEventId()))
				.orElseThrow(PersonalEventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserPersonalEvent(user,personalEvent);

		List<InvitationModel> invitationModels = request.getInviteeEmail().stream()
				.map(email -> {
					Invitation invitation = saveInvitation(email, personalEvent);
					sendEmail(personalEvent, invitation);
					return conversionService.convert(invitation,InvitationModel.class);
				})
				.toList();


		return CreateInvitationResponse
				.builder()
				.invitationModel(invitationModels)
				.build();
	}

	private Invitation saveInvitation(String email, PersonalEvent personalEvent) {
		Invitation invitation = Invitation.builder()
				.inviteeEmail(email)
				.invitationStatus(InvitationStatus.PENDING)
				.personalEvent(personalEvent)
				.build();

		return invitationRepository.save(invitation);
	}
	private void sendEmail(PersonalEvent personalEvent, Invitation invitation) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("inviteeName", invitation.getInviteeEmail());
		templateModel.put("eventName", personalEvent.getEventName());
		templateModel.put("eventDate", personalEvent.getEventDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		templateModel.put("eventLocation", personalEvent.getEventLocation());
		templateModel.put("eventDescription", personalEvent.getEventDescription());
		templateModel.put("invitationLink", "http://localhost:8020/swagger-ui/index.html#/invitation-controller/answerInvitation");
		MailerRequest mailerRequest = MailerRequest.builder()
				.to(invitation.getInviteeEmail())
				.subject("You're Invited to " + personalEvent.getEventName())
				.templateName("invitationTemplate")
				.templateModel(templateModel)
				.build();

		mailerOperation.process(mailerRequest);
	}

}
