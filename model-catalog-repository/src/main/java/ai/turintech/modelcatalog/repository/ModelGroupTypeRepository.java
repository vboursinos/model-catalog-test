package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelGroupType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ModelGroupType entity. */
@SuppressWarnings("unused")
@Repository
public interface ModelGroupTypeRepository extends JpaRepository<ModelGroupType, UUID> {}
