package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudCallable;
import ai.turintech.components.jpa.search.service.AbstractSearchService;
import ai.turintech.components.jpa.search.service.SearchService;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelLimited;
import ai.turintech.modelcatalog.repository.ModelRepository;
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

/** Service Implementation for managing {@link Model}. */
@Service
@Transactional
public class ModelServiceImpl extends AbstractSearchService<ModelLimited, ModelDTO>
    implements SearchService<ModelDTO>, ModelService {
  private Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ModelRepository modelRepository;
  @Autowired private ModelMapper modelMapper;

  public ModelServiceImpl() {
    super(ModelLimited.class, ModelDTO.class);
  }

  /**
   * Save a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> save(ModelDTO modelDTO) {
    log.debug("Request to save Model : {}", modelDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<ModelDTO, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "create",
            modelDTO,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> update(ModelDTO modelDTO) {
    log.debug("Request to update Model : {}", modelDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<ModelDTO, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "update",
            modelDTO,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a model.
   *
   * @param modelDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> partialUpdate(ModelDTO modelDTO) {
    log.debug("Request to partially update Model : {}", modelDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<ModelDTO, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "partialUpdate",
            modelDTO.getId(),
            modelDTO,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the models.
   *
   * @return the list of entities.
   */
  public Flux<ModelDTO> findAll() {
    log.debug("Request to get all Models");
    return Flux.defer(
            () -> Flux.fromStream(modelRepository.findAll().stream().map(modelMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one model by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelDTO> findOne(UUID id) {
    log.debug("Request to get Model : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<ModelDTO, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "findById",
            id,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the model by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete Model : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<Void, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "delete",
            id,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<Boolean, ModelDTO, Model, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "existsById",
            id,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
