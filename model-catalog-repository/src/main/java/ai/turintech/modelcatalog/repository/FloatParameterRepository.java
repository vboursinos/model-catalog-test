package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.FloatParameter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the FloatParameter entity. */
@SuppressWarnings("unused")
@Repository
public interface FloatParameterRepository extends JpaRepository<FloatParameter, UUID> {}
