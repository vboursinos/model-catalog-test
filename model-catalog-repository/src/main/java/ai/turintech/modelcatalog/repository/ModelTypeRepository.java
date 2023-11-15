package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ModelType entity. */
@SuppressWarnings("unused")
@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, UUID> {}
