package database.repository;

import database.entity.Metric;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the Metric entity. */
@SuppressWarnings("unused")
@Repository
public interface MetricRepository extends JpaRepository<Metric, UUID> {}
