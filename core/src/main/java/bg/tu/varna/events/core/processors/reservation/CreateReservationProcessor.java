package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.*;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationOperation;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationRequest;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationResponse;
import bg.tu.varna.events.core.utils.QrCodeGenerationUtils;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.EventStatus;
import bg.tu.varna.events.persistence.enums.OrganizationStatus;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReservationProcessor implements CreateReservationOperation {

	private final EventRepository eventRepository;
	private final ReservationRepository reservationRepository;
	private final ValidationUtils 	validationUtils;
	private final ConversionService conversionService;
	private final MailerOperation mailerOperation;
	private final QrCodeGenerationUtils qrCodeGenerationUtils;
	@Value("${client.url}")
	String clientUrl;
	@Override
	public CreateReservationResponse process(CreateReservationRequest request) {
		Event event = eventRepository.findById(UUID.fromString(request.getEventId()))
				.orElseThrow(EventNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();

		if(event.getOrganization().getOrganizationStatus().equals(OrganizationStatus.SUSPENDED))
			throw new OrganizationSuspendedException();

		if(event.getStatus().equals(EventStatus.SUSPENDED) || event.getStatus().equals(EventStatus.EXPIRED))
			throw new EventSuspendedException();

		Reservation reservation = saveReservation(request, event, user);
		updateEventCapacity(request, event);

		sendEmail(user,event,reservation);

		ReservationModel reservationModel = conversionService.convert(reservation, ReservationModel.class);

		return CreateReservationResponse.builder()
				.reservationModel(reservationModel)
				.build();
	}
	private Reservation saveReservation(CreateReservationRequest request,Event event, User user) {
		Reservation reservation = Reservation.builder()
				.event(event)
				.numberOfPeople(request.getNumberOfPeople())
				.user(user)
				.reservationTime(LocalDateTime.now())
				.reservationDateTime(event.getEventDateTime())
				.reservationStatus(ReservationStatus.ACTIVE)
				.isPaid(request.getPayNow())
				.build();

		reservationRepository.save(reservation);
		return reservation;
	}

	private void updateEventCapacity(CreateReservationRequest request, Event event) {
		if(event.getCapacity() < request.getNumberOfPeople()) {
			throw new TicketSoldOutException();
		}
		event.setCapacity(event.getCapacity() - request.getNumberOfPeople());
		eventRepository.save(event);
	}

	private void sendEmail(User user, Event event, Reservation reservation) {
		BigDecimal ticketPrice = new BigDecimal(event.getTicketPrice().toString());
		BigDecimal totalAmount = ticketPrice.multiply(new BigDecimal(reservation.getNumberOfPeople()));

		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", user.getEmail());
		templateModel.put("event", event.getEventName());
		templateModel.put("description", event.getEventDescription());
		templateModel.put("datetime",event.getEventDateTime());
		templateModel.put("paid",reservation.getIsPaid());
		templateModel.put("quantity",reservation.getNumberOfPeople());
		templateModel.put("total", totalAmount);
		templateModel.put("qrCodeImage",generateQrCode(reservation));
		templateModel.put("eventsLink",
				clientUrl + "/business-event/get_all_by_organization?organizationId=" + event.getOrganization().getOrganizationId()
						+ "&includeSuspended=false");

		// Send email
		MailerRequest mailerRequest = MailerRequest.builder()
				.to(user.getEmail())
				.subject("Reservation Confirmation")
				.templateName("confirmedReservationTemplate")
				.templateModel(templateModel)
				.build();

		mailerOperation.process(mailerRequest);
	}

	private String generateQrCode(Reservation reservation) {
		String verificationUrl = clientUrl
				.concat("/reservations/get?reservationId=")
				.concat(String.valueOf(reservation.getReservationId()));
		byte[] qrCodeImage;
		try {
			qrCodeImage = qrCodeGenerationUtils.generateQRCodeImage(verificationUrl);
		} catch (Exception e) {
			throw new QrGenerationFailedException();
		}
		return Base64.getEncoder().encodeToString(qrCodeImage);
	}

}
