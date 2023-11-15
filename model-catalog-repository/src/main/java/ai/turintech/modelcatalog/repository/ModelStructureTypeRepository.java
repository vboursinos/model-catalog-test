package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ModelStructureType entity. */
@SuppressWarnings("unused")
@Repository
public interface ModelStructureTypeRepository extends JpaRepository<ModelStructureType, UUID> {}
