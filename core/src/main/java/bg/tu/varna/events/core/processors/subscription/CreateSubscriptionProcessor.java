package bg.tu.varna.events.core.processors.subscription;

import bg.tu.varna.events.api.exceptions.OrganizationNotFoundException;
import bg.tu.varna.events.api.exceptions.OrganizationSuspendedException;
import bg.tu.varna.events.api.exceptions.SubscriptionActiveException;
import bg.tu.varna.events.api.model.SubscriptionModel;
import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.Subscription;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import bg.tu.varna.events.persistence.repositories.OrganizationRepository;
import bg.tu.varna.events.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSubscriptionProcessor implements CreateSubscriptionOperation {

	private final SubscriptionRepository subscriptionRepository;
	private final OrganizationRepository organizationRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;
	private final MailerOperation mailerOperation;
	@Value("${client.url}")
	String clientUrl;

	@Override
	public CreateSubscriptionResponse process(CreateSubscriptionRequest request) {
		User user = validationUtils.getCurrentAuthenticatedUser();
		Organization organization = organizationRepository.findById(UUID.fromString(request.getOrganizationId()))
				.orElseThrow(OrganizationNotFoundException::new);

		if (organization.getOrganizationStatus().equals(OrganizationStatus.SUSPENDED))
			throw new OrganizationSuspendedException();

		subscriptionRepository.findByUserAndOrganizationAndSubscriptionStatus(
				user,
				organization,
				SubscriptionStatus.ACTIVE
		).ifPresent(subscription -> {
			throw new SubscriptionActiveException();
		});

		Subscription subscription = saveSubscription(user, organization);

		sendEmail(user, organization);

		SubscriptionModel subscriptionModel = conversionService.convert(subscription, SubscriptionModel.class);

		return CreateSubscriptionResponse.builder()
				.subscriptionModel(subscriptionModel)
				.build();
	}

	private void sendEmail(User user, Organization organization) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", user.getEmail());
		templateModel.put("organization", organization.getOrganizationName().toUpperCase());
		templateModel.put("eventsLink",
				clientUrl + "/business-event/get_all_by_organization?organizationId=" + organization.getOrganizationId()
						+ "&includeSuspended=false");
		templateModel.put("unsubscribeLink",
				clientUrl + "/subscriptions/cancel?organizationId=" + organization.getOrganizationId() + "&userId=" + user.getUserId());

		// Send email
		MailerRequest mailerRequest = MailerRequest.builder()
				.to(user.getEmail())
				.subject("Subscription Confirmation")
				.templateName("confirmedSubscriptionTemplate")
				.templateModel(templateModel)
				.build();

		mailerOperation.process(mailerRequest);
	}

	private Subscription saveSubscription(User user, Organization organization) {
		Subscription subscription = Subscription.builder()
				.user(user)
				.organization(organization)
				.reservationTime(LocalDateTime.now())
				.subscriptionStatus(SubscriptionStatus.ACTIVE)
				.build();

		subscriptionRepository.save(subscription);
		return subscription;
	}
}
