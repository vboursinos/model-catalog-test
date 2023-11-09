package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the Parameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {}
