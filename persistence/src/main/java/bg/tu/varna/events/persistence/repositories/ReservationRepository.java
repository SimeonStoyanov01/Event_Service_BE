package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
	List<Reservation> findAllByUser(User user);

	List<Reservation> findAllByEventAndReservationStatus(Event event, ReservationStatus reservationStatus);
	List<Reservation> findAllByEventAndReservationStatusNot(Event event, ReservationStatus reservationStatus);
	List<Reservation> findAllByEventAndReservationStatusNotAndIsPaid(Event event, ReservationStatus reservationStatus, Boolean isPaid);



}