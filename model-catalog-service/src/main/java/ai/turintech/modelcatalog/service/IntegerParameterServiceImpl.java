package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterMapper;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
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

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
@Transactional
public class IntegerParameterServiceImpl implements IntegerParameterService {

  private final Logger log = LoggerFactory.getLogger(IntegerParameterServiceImpl.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;

  @Autowired private IntegerParameterRepository integerParameterRepository;

  @Autowired private IntegerParameterMapper integerParameterMapper;

  /**
   * Save a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to save IntegerParameter : {}", integerParameterDTO);
    GenericModelCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "create",
            integerParameterDTO,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to update IntegerParameter : {}", integerParameterDTO);
    GenericModelCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "update",
            integerParameterDTO,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a integerParameter.
   *
   * @param integerParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to partially update IntegerParameter : {}", integerParameterDTO);
    GenericModelCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "partialUpdate",
            integerParameterDTO.getParameterTypeDefinitionId(),
            integerParameterDTO,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the integerParameters.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<IntegerParameterDTO>> findAll() {
    log.debug("Request to get all IntegerParameters");
    GenericModelCallable<List<IntegerParameterDTO>, IntegerParameterDTO, IntegerParameter>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "findAll",
                integerParameterRepository,
                integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<IntegerParameterDTO> findAllStream() {
    log.debug("Request to get all IntegerParameters");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    integerParameterRepository.findAll().stream().map(integerParameterMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one integerParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<IntegerParameterDTO> findOne(UUID id) {
    log.debug("Request to get IntegerParameter : {}", id);
    GenericModelCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "findById",
            id,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the integerParameter by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete IntegerParameter : {}", id);
    GenericModelCallable<Void, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "delete",
            id,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, IntegerParameterDTO, IntegerParameter> callable =
        context.getBean(
            GenericModelCallable.class,
            "existsById",
            id,
            integerParameterRepository,
            integerParameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
