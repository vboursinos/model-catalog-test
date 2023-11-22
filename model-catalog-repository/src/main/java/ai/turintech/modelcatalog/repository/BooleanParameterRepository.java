package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.BooleanParameter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the BooleanParameter entity */
@SuppressWarnings("unused")
@Repository
public interface BooleanParameterRepository extends JpaRepository<BooleanParameter, UUID> {}
