package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Organization;
import bg.tu.varna.events.persistence.entities.Subscription;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
	List<Subscription> findAllByUser(User user);

	Optional<Subscription> findByUserAndOrganizationAndSubscriptionStatus(
			User user,
			Organization organization,
			SubscriptionStatus subscriptionStatus
	);

	List<Subscription> findAllByOrganizationAndSubscriptionStatus(Organization organization, SubscriptionStatus subscriptionStatus);

}