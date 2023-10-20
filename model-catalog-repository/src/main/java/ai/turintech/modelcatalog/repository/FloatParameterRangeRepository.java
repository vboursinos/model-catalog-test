package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.FloatParameterRange;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the FloatParameterRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloatParameterRangeRepository
    extends ReactiveCrudRepository<FloatParameterRange, Long>, FloatParameterRangeRepositoryInternal {
    @Override
    <S extends FloatParameterRange> Mono<S> save(S entity);

    @Override
    Flux<FloatParameterRange> findAll();

    @Override
    Mono<FloatParameterRange> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface FloatParameterRangeRepositoryInternal {
    <S extends FloatParameterRange> Mono<S> save(S entity);

    Flux<FloatParameterRange> findAllBy(Pageable pageable);

    Flux<FloatParameterRange> findAll();

    Mono<FloatParameterRange> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<FloatParameterRange> findAllBy(Pageable pageable, Criteria criteria);
}
