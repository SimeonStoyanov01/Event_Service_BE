package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Invitation;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PersonalEventRepository extends JpaRepository<PersonalEvent, UUID> {

}