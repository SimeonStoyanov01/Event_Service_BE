package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Reservation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

}