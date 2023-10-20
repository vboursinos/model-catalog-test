package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ParameterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterTypeRepository extends ReactiveCrudRepository<ParameterType, UUID>, ParameterTypeRepositoryInternal {
    @Query("SELECT * FROM parameter_type entity WHERE entity.parameter_id = :id")
    Flux<ParameterType> findByParameter(UUID id);

    @Query("SELECT * FROM parameter_type entity WHERE entity.parameter_id IS NULL")
    Flux<ParameterType> findAllWhereParameterIsNull();

    @Query("SELECT * FROM parameter_type entity WHERE entity.parameter_type_definition_id = :id")
    Flux<ParameterType> findByParameterTypeDefinition(UUID id);

    @Query("SELECT * FROM parameter_type entity WHERE entity.parameter_type_definition_id IS NULL")
    Flux<ParameterType> findAllWhereParameterTypeDefinitionIsNull();

    @Override
    <S extends ParameterType> Mono<S> save(S entity);

    @Override
    Flux<ParameterType> findAll();

    @Override
    Mono<ParameterType> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface ParameterTypeRepositoryInternal {
    <S extends ParameterType> Mono<S> save(S entity);

    Flux<ParameterType> findAllBy(Pageable pageable);

    Flux<ParameterType> findAll();

    Mono<ParameterType> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ParameterType> findAllBy(Pageable pageable, Criteria criteria);
}
