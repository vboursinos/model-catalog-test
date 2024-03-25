package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.DependencyGroupType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the MlTaskType entity. */
@SuppressWarnings("unused")
@Repository
public interface DependencyGroupTypeRepository extends JpaRepository<DependencyGroupType, UUID> {}
