package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
	List<Invitation> findAllByPersonalEvent(PersonalEvent personalEvent);

}