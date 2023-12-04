package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallableImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterValueMapper;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.repository.IntegerParameterValueRepository;
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

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Service
@Transactional
public class IntegerParameterValueServiceImpl implements IntegerParameterValueService {

  private final Logger log = LoggerFactory.getLogger(IntegerParameterValueServiceImpl.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;
  @Autowired private IntegerParameterValueRepository integerParameterValueRepository;
  @Autowired private IntegerParameterValueMapper integerParameterValueMapper;

  /**
   * Save a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterValueDTO> save(IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to save IntegerParameterValue : {}", integerParameterValueDTO);
    GenericModelCallableImpl<
            IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "create",
                integerParameterValueDTO,
                integerParameterValueRepository,
                integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterValueDTO> update(IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to update IntegerParameterValue : {}", integerParameterValueDTO);
    GenericModelCallableImpl<
            IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "update",
                integerParameterValueDTO,
                integerParameterValueRepository,
                integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterValueDTO> partialUpdate(
      IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to partially update IntegerParameterValue : {}", integerParameterValueDTO);
    GenericModelCallableImpl<
            IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "partialUpdate",
                integerParameterValueDTO.getId(),
                integerParameterValueDTO,
                integerParameterValueRepository,
                integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the integerParameterValues.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<IntegerParameterValueDTO>> findAll() {
    log.debug("Request to get all IntegerParameterValues");
    GenericModelCallableImpl<
            List<IntegerParameterValueDTO>, IntegerParameterValueDTO, IntegerParameterValue>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "findAll",
                integerParameterValueRepository,
                integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<IntegerParameterValueDTO> findAllStream() {
    log.debug("Request to get all IntegerParameterValues");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    integerParameterValueRepository.findAll().stream()
                        .map(integerParameterValueMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<IntegerParameterValueDTO> findOne(UUID id) {
    log.debug("Request to get IntegerParameterValue : {}", id);
    GenericModelCallableImpl<
            IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "findById",
                id,
                integerParameterValueRepository,
                integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the integerParameterValue by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete IntegerParameterValue : {}", id);
    GenericModelCallableImpl<Void, IntegerParameterValueDTO, IntegerParameterValue> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "delete",
            id,
            integerParameterValueRepository,
            integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallableImpl<Boolean, IntegerParameterValueDTO, IntegerParameterValue> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "existsById",
            id,
            integerParameterValueRepository,
            integerParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
