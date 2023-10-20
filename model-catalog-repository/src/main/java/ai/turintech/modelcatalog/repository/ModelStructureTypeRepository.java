package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ModelStructureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelStructureTypeRepository
    extends ReactiveCrudRepository<ModelStructureType, UUID>, ModelStructureTypeRepositoryInternal {
    @Query("SELECT * FROM model_structure_type entity WHERE entity.model_id = :id")
    Flux<ModelStructureType> findByModel(UUID id);

    @Query("SELECT * FROM model_structure_type entity WHERE entity.model_id IS NULL")
    Flux<ModelStructureType> findAllWhereModelIsNull();

    @Override
    <S extends ModelStructureType> Mono<S> save(S entity);

    @Override
    Flux<ModelStructureType> findAll();

    @Override
    Mono<ModelStructureType> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface ModelStructureTypeRepositoryInternal {
    <S extends ModelStructureType> Mono<S> save(S entity);

    Flux<ModelStructureType> findAllBy(Pageable pageable);

    Flux<ModelStructureType> findAll();

    Mono<ModelStructureType> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ModelStructureType> findAllBy(Pageable pageable, Criteria criteria);
}
