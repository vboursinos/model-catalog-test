package database.repository;

import database.entity.IntegerParameterValue;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the IntegerParameterValue entity. */
@SuppressWarnings("unused")
@Repository
public interface IntegerParameterValueRepository
    extends JpaRepository<IntegerParameterValue, UUID> {}
