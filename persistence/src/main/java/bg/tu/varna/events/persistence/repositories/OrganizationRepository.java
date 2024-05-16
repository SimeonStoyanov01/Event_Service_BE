package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.Organization;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

}