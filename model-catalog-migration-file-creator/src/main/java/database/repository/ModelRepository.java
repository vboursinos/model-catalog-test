package database.repository;

import database.entity.Model;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Spring Data JPA repository for the Model entity.
 *
 * <p>When extending this class, extend ModelRepositoryWithBagRelationships too. For more
 * information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Component
public interface ModelRepository extends JpaRepository<Model, UUID> {
  Optional<Model> findByName(String name);
}
