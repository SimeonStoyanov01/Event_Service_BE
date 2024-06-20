package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.ReservationNotFoundException;
import bg.tu.varna.events.api.exceptions.ReservationPaidException;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationOperation;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationRequest;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePaymentStatusReservationProcessor implements ChangePaymentStatusReservationOperation {
	private final ReservationRepository reservationRepository;
	private final ValidationUtils validationUtils;
	@Override
	public ChangePaymentStatusReservationResponse process(ChangePaymentStatusReservationRequest request) {
		Reservation reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()))
				.orElseThrow(ReservationNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserBusinessOrganization(user,reservation.getEvent().getOrganization());

		if(reservation.getIsPaid())
			throw new ReservationPaidException();

		reservation.setIsPaid(true);
		reservationRepository.save(reservation);

		return ChangePaymentStatusReservationResponse
				.builder()
				.message("Reservation is paid")
				.build();
	}
}
