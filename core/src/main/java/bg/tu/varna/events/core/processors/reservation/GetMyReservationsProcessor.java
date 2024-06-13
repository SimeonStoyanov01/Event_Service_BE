package bg.tu.varna.events.core.processors.reservation;

import bg.tu.varna.events.api.exceptions.EmptyReservationsListException;
import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsOperation;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsRequest;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyReservationsProcessor implements GetMyReservationsOperation {
	private final ReservationRepository reservationRepository;
	private final ConversionService conversionService;
	private final ValidationUtils validationUtils;

	@Override
	public GetMyReservationsResponse process(GetMyReservationsRequest request) {
		User user = validationUtils.getCurrentAuthenticatedUser();

		List<ReservationModel> reservationModels = reservationRepository.findAllByUser(user)
				.stream()
				.map(reservation -> conversionService.convert(reservation, ReservationModel.class))
				.toList();
		if(reservationModels.isEmpty())
			throw new EmptyReservationsListException();

		return GetMyReservationsResponse.builder()
				.reservationModels(reservationModels)
				.build();
	}
}
