package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.ReservationActiveException;
import bg.tu.varna.events.api.exceptions.ReservationNotFoundException;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationOperation;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationRequest;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteReservationProcessor implements DeleteReservationOperation {
	private final ReservationRepository reservationRepository;
	private final ValidationUtils validationUtils;

	@Override
	public DeleteReservationResponse process(DeleteReservationRequest request) {
		Reservation reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()))
				.orElseThrow(ReservationNotFoundException::new);
		validationUtils.validateUserBusinessOrganization(validationUtils.getCurrentAuthenticatedUser()
				,reservation.getEvent().getOrganization());
		if(reservation.getReservationStatus().equals(ReservationStatus.ACTIVE))
			throw new ReservationActiveException();

		reservationRepository.deleteById(UUID.fromString(request.getReservationId()));

		return DeleteReservationResponse.builder()
				.message("Reservation deleted successfully.")
				.build();	}
}
