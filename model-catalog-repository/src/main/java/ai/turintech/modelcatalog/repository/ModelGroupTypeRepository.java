package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the ModelGroupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelGroupTypeRepository extends JpaRepository<ModelGroupType, UUID> {}
