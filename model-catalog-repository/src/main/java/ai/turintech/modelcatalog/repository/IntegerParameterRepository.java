package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.IntegerParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the IntegerParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegerParameterRepository extends ReactiveCrudRepository<IntegerParameter, Long>, IntegerParameterRepositoryInternal {
    @Override
    <S extends IntegerParameter> Mono<S> save(S entity);

    @Override
    Flux<IntegerParameter> findAll();

    @Override
    Mono<IntegerParameter> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface IntegerParameterRepositoryInternal {
    <S extends IntegerParameter> Mono<S> save(S entity);

    Flux<IntegerParameter> findAllBy(Pageable pageable);

    Flux<IntegerParameter> findAll();

    Mono<IntegerParameter> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<IntegerParameter> findAllBy(Pageable pageable, Criteria criteria);
}
