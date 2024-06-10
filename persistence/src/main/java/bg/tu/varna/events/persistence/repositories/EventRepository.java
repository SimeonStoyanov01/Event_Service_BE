package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.entities.OrganizationStatus;
import bg.tu.varna.events.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
	List<Event> findByStatusNotAndOrganizationOrganizationStatusNot(EventStatus eventStatus, OrganizationStatus organizationStatus);
	List<Event> findByOrganizationOrganizationStatusNot(OrganizationStatus organizationStatus);
	List<Event> findAllByOrganizationOrganizationId(UUID organizationId);

	List<Event> findAllByOrganizationOrganizationIdAndStatusNot(UUID organizationId, EventStatus status);

	List<Event> findAllByUser(User user);

	List<Event> findAllByUserAndStatusNot(User user, EventStatus status);
	List<Event> findByEventDateTimeBeforeAndStatusNot(LocalDateTime localDateTime, EventStatus eventStatus);




}