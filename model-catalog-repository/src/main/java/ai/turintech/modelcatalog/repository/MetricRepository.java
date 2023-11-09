package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the Metric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetricRepository extends JpaRepository<Metric, UUID> {}
