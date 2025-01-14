package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ParameterType entity. */
@SuppressWarnings("unused")
@Repository
public interface ParameterTypeRepository extends JpaRepository<ParameterType, UUID> {}
