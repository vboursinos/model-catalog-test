package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ParameterTypeDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterTypeDefinitionRepository
    extends ReactiveCrudRepository<ParameterTypeDefinition, UUID>, ParameterTypeDefinitionRepositoryInternal {
    @Override
    <S extends ParameterTypeDefinition> Mono<S> save(S entity);

    @Override
    Flux<ParameterTypeDefinition> findAll();

    @Override
    Mono<ParameterTypeDefinition> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface ParameterTypeDefinitionRepositoryInternal {
    <S extends ParameterTypeDefinition> Mono<S> save(S entity);

    Flux<ParameterTypeDefinition> findAllBy(Pageable pageable);

    Flux<ParameterTypeDefinition> findAll();

    Mono<ParameterTypeDefinition> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ParameterTypeDefinition> findAllBy(Pageable pageable, Criteria criteria);
}
