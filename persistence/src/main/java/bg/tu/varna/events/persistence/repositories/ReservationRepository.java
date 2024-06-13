package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Reservation;
import bg.tu.varna.events.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
	List<Reservation> findAllByUser(User user);

}