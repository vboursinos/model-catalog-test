package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.FloatParameterRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the FloatParameterRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloatParameterRangeRepository extends JpaRepository<FloatParameterRange, UUID> {}
