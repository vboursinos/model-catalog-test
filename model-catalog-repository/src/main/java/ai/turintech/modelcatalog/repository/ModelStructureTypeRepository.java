package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelStructureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the ModelStructureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelStructureTypeRepository extends JpaRepository<ModelStructureType, UUID> {}
