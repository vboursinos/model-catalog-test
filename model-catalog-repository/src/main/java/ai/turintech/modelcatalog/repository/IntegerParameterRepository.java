package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the IntegerParameter entity. */
@SuppressWarnings("unused")
@Repository
public interface IntegerParameterRepository extends JpaRepository<IntegerParameter, UUID> {}
