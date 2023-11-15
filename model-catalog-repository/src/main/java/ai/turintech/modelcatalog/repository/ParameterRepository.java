package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.Parameter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the Parameter entity. */
@SuppressWarnings("unused")
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {}
