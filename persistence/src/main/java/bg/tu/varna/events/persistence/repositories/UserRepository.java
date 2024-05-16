package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}