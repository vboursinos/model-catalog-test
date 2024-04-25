package database.repository;

import database.entity.CategoricalParameter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the CategoricalParameter entity. */
@SuppressWarnings("unused")
@Repository
public interface CategoricalParameterRepository extends JpaRepository<CategoricalParameter, UUID> {}
