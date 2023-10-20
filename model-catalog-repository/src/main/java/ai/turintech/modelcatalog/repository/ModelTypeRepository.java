package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ModelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelTypeRepository extends ReactiveCrudRepository<ModelType, UUID>, ModelTypeRepositoryInternal {
    @Query("SELECT * FROM model_type entity WHERE entity.models_id = :id")
    Flux<ModelType> findByModels(UUID id);

    @Query("SELECT * FROM model_type entity WHERE entity.models_id IS NULL")
    Flux<ModelType> findAllWhereModelsIsNull();

    @Override
    <S extends ModelType> Mono<S> save(S entity);

    @Override
    Flux<ModelType> findAll();

    @Override
    Mono<ModelType> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface ModelTypeRepositoryInternal {
    <S extends ModelType> Mono<S> save(S entity);

    Flux<ModelType> findAllBy(Pageable pageable);

    Flux<ModelType> findAll();

    Mono<ModelType> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ModelType> findAllBy(Pageable pageable, Criteria criteria);
}
