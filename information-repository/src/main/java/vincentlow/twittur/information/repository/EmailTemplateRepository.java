package vincentlow.twittur.information.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vincentlow.twittur.information.model.entity.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {

  Page<EmailTemplate> findAll(Pageable pageable);

  EmailTemplate findByTemplateCodeAndMarkForDeleteFalse(String templateCode);
}
