package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterValueMapper;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.repository.CategoricalParameterValueRepository;
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

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Service
@Transactional
public class CategoricalParameterValueService {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterValueService.class);
  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private CategoricalParameterValueRepository categoricalParameterValueRepository;
  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;

  /**
   * Save a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<CategoricalParameterValueDTO> save(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug("Request to save CategoricalParameterValue : {}", categoricalParameterValueDTO);
    GenericModelCallable<
            CategoricalParameterValueDTO, CategoricalParameterValueDTO, CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "create",
                categoricalParameterValueDTO,
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<CategoricalParameterValueDTO> update(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug("Request to update CategoricalParameterValue : {}", categoricalParameterValueDTO);
    GenericModelCallable<
            CategoricalParameterValueDTO, CategoricalParameterValueDTO, CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "update",
                categoricalParameterValueDTO,
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<CategoricalParameterValueDTO> partialUpdate(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug(
        "Request to partially update CategoricalParameterValue : {}", categoricalParameterValueDTO);
    GenericModelCallable<
            CategoricalParameterValueDTO, CategoricalParameterValueDTO, CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "partialUpdate",
                categoricalParameterValueDTO.getId(),
                categoricalParameterValueDTO,
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the categoricalParameterValues.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<CategoricalParameterValueDTO>> findAll() {
    log.debug("Request to get all CategoricalParameterValues");
    GenericModelCallable<
            List<CategoricalParameterValueDTO>,
            CategoricalParameterValueDTO,
            CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "findAll",
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<CategoricalParameterValueDTO> findAllStream() {
    log.debug("Request to get all CategoricalParameterValues");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    categoricalParameterValueRepository.findAll().stream()
                        .map(categoricalParameterValueMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<CategoricalParameterValueDTO> findOne(UUID id) {
    log.debug("Request to get CategoricalParameterValue : {}", id);
    GenericModelCallable<
            CategoricalParameterValueDTO, CategoricalParameterValueDTO, CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "findById",
                id,
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete CategoricalParameterValue : {}", id);
    GenericModelCallable<Void, CategoricalParameterValueDTO, CategoricalParameterValue> callable =
        context.getBean(
            GenericModelCallable.class,
            "delete",
            id,
            categoricalParameterValueRepository,
            categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, CategoricalParameterValueDTO, CategoricalParameterValue>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "existsById",
                id,
                categoricalParameterValueRepository,
                categoricalParameterValueMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}