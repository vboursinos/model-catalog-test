package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the ParameterDistributionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterDistributionTypeRepository extends JpaRepository<ParameterDistributionType, UUID> {}
