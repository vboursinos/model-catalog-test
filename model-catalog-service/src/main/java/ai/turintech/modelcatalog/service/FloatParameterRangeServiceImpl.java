package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.callable.GenericModelCallableImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterRangeMapper;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/** Service Implementation for managing {@link FloatParameterRange}. */
@Service
@Transactional
public class FloatParameterRangeServiceImpl extends ReactiveAbstractCrudServiceImpl<FloatParameterRangeDTO,FloatParameterRange,UUID> implements FloatParameterRangeService {

  private final Logger log = LoggerFactory.getLogger(FloatParameterRangeServiceImpl.class);
  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private FloatParameterRangeRepository floatParameterRangeRepository;
  @Autowired private FloatParameterRangeMapper floatParameterRangeMapper;

  /**
   * Save a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<FloatParameterRangeDTO> save(FloatParameterRangeDTO floatParameterRangeDTO) {
    log.debug("Request to save FloatParameterRange : {}", floatParameterRangeDTO);
    GenericModelCallable<FloatParameterRangeDTO, FloatParameterRangeDTO, FloatParameterRange>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "create",
                floatParameterRangeDTO,
                floatParameterRangeRepository,
                floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<FloatParameterRangeDTO> update(FloatParameterRangeDTO floatParameterRangeDTO) {
    log.debug("Request to update FloatParameterRange : {}", floatParameterRangeDTO);
    GenericModelCallable<FloatParameterRangeDTO, FloatParameterRangeDTO, FloatParameterRange>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "update",
                floatParameterRangeDTO,
                floatParameterRangeRepository,
                floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<FloatParameterRangeDTO> partialUpdate(FloatParameterRangeDTO floatParameterRangeDTO) {
    log.debug("Request to partially update FloatParameterRange : {}", floatParameterRangeDTO);
    GenericModelCallable<FloatParameterRangeDTO, FloatParameterRangeDTO, FloatParameterRange>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "partialUpdate",
                floatParameterRangeDTO.getId(),
                floatParameterRangeDTO,
                floatParameterRangeRepository,
                floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the floatParameterRanges.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<FloatParameterRangeDTO>> findAll() {
    log.debug("Request to get all FloatParameterRanges");
    GenericModelCallable<List<FloatParameterRangeDTO>, FloatParameterRangeDTO, FloatParameterRange>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "findAll",
                floatParameterRangeRepository,
                floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<FloatParameterRangeDTO> findAllStream() {
    log.debug("Request to get all FloatParameterRanges");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    floatParameterRangeRepository.findAll().stream()
                        .map(floatParameterRangeMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }
  /**
   * Get one floatParameterRange by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<FloatParameterRangeDTO> findOne(UUID id) {
    log.debug("Request to get FloatParameterRange : {}", id);
    GenericModelCallable<FloatParameterRangeDTO, FloatParameterRangeDTO, FloatParameterRange>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "findById",
                id,
                floatParameterRangeRepository,
                floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the floatParameterRange by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete FloatParameterRange : {}", id);
    GenericModelCallable<Void, FloatParameterRangeDTO, FloatParameterRange> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "delete",
            id,
            floatParameterRangeRepository,
            floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, FloatParameterRangeDTO, FloatParameterRange> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "existsById",
            id,
            floatParameterRangeRepository,
            floatParameterRangeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
