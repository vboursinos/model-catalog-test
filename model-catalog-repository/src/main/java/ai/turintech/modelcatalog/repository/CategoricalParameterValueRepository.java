package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the CategoricalParameterValue entity. */
@SuppressWarnings("unused")
@Repository
public interface CategoricalParameterValueRepository
    extends JpaRepository<CategoricalParameterValue, UUID> {}
