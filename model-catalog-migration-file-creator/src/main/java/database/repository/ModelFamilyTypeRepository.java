package database.repository;

import database.entity.ModelFamilyType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ModelFamilyType entity. */
@SuppressWarnings("unused")
@Repository
public interface ModelFamilyTypeRepository extends JpaRepository<ModelFamilyType, UUID> {}
