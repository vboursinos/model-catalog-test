package database.repository;

import database.entity.ParameterDistributionType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the ParameterDistributionType entity. */
@SuppressWarnings("unused")
@Repository
public interface ParameterDistributionTypeRepository
    extends JpaRepository<ParameterDistributionType, UUID> {}
