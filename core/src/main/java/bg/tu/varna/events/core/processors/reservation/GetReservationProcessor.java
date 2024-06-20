package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.ReservationNotFoundException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationOperation;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationRequest;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetReservationProcessor implements GetReservationOperation {
	private final ReservationRepository reservationRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;

	@Override
	public GetReservationResponse process(GetReservationRequest request) {
		Reservation reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()))
				.orElseThrow(ReservationNotFoundException::new);

		User user = validationUtils.getCurrentAuthenticatedUser();
		validationUtils.validateUserReservation(user,reservation);
		validationUtils.validateUserBusinessOrganization(user,reservation.getEvent().getOrganization());
		ReservationModel reservationModel = conversionService.convert(reservation, ReservationModel.class);
		return GetReservationResponse
				.builder()
				.reservationModel(reservationModel)
				.build();
	}
}
