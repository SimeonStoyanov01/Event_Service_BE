package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
	Optional<Organization> findOrganizationByOrganizationName(String organizationName);

	Optional<Organization> findOrganizationByOrganizationId(UUID organizationId);


}