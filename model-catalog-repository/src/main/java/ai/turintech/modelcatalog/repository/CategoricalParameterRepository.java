package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.CategoricalParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the CategoricalParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoricalParameterRepository extends JpaRepository<CategoricalParameter, UUID> {}
