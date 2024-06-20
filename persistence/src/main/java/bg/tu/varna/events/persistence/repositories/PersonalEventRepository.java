package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.PersonalEvent;
import bg.tu.varna.events.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonalEventRepository extends JpaRepository<PersonalEvent, UUID> {
	List<PersonalEvent> findAllByUser(User user);

}