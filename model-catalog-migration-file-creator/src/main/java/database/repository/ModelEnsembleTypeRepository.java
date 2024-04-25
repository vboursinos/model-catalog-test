package database.repository;

import database.entity.ModelEnsembleType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ModelEnsembleType entity. */
@SuppressWarnings("unused")
@Repository
public interface ModelEnsembleTypeRepository extends JpaRepository<ModelEnsembleType, UUID> {}
