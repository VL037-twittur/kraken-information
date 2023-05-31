package vincentlow.twittur.information.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vincentlow.twittur.information.model.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
}
