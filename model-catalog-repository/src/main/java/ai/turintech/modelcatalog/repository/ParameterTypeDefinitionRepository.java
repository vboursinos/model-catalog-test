package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ParameterTypeDefinition entity. */
@SuppressWarnings("unused")
@Repository
public interface ParameterTypeDefinitionRepository
    extends JpaRepository<ParameterTypeDefinition, UUID> {}
