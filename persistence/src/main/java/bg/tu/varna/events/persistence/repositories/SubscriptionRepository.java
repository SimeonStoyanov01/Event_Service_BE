package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

}