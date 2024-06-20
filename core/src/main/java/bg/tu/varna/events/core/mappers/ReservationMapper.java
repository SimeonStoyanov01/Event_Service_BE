package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.ReservationModel;
import bg.tu.varna.events.persistence.entities.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper implements Converter<Reservation, ReservationModel> {


	@Override
	public ReservationModel convert(Reservation source) {
		return ReservationModel.builder()
				.reservationId(source.getReservationId())
				.userId(source.getUser().getUserId())
				.eventId(source.getEvent().getEventId())
				.numberOfPeople(source.getNumberOfPeople())
				.reservationDateTime(source.getReservationDateTime())
				.reservationStatus(source.getReservationStatus())
				.build();
	}
}
