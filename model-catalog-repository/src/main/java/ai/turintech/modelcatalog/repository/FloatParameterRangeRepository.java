package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the FloatParameterRange entity. */
@SuppressWarnings("unused")
@Repository
public interface FloatParameterRangeRepository extends JpaRepository<FloatParameterRange, UUID> {}