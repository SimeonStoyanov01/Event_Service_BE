package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.Invitation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

}