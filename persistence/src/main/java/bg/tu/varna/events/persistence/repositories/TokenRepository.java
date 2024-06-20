package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
	@Query(value = """
        select t from Token t join User u
        on t.user.userId = u.userId
        where u.userId = :id and (t.expired = false or t.revoked = false)
    """)
	List<Token> findAllValidTokenByUser(UUID id);

	Optional<Token> findByToken(String token);
}
