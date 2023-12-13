package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudCallable;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterMapper;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.repository.ParameterRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/** Service Implementation for managing {@link Parameter}. */
@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

  private final Logger log = LoggerFactory.getLogger(ParameterServiceImpl.class);

  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ParameterRepository parameterRepository;

  @Autowired private ParameterMapper parameterMapper;

  /**
   * Save a parameter.
   *
   * @param parameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterDTO> save(ParameterDTO parameterDTO) {
    log.debug("Request to save Parameter : {}", parameterDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<ParameterDTO, ParameterDTO, Parameter, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "create",
            parameterDTO,
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a parameter.
   *
   * @param parameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterDTO> update(ParameterDTO parameterDTO) {
    log.debug("Request to update Parameter : {}", parameterDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<ParameterDTO, ParameterDTO, Parameter, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "update",
            parameterDTO,
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a parameter.
   *
   * @param parameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<Optional<ParameterDTO>> partialUpdate(ParameterDTO parameterDTO) {
    log.debug("Request to partially update Parameter : {}", parameterDTO);
    ReactiveAbstractUUIDIdentityCrudCallable<Optional<ParameterDTO>, ParameterDTO, Parameter, UUID>
        callable =
            context.getBean(
                ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
                "partialUpdate",
                parameterDTO.getId(),
                parameterDTO,
                parameterRepository,
                parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameters.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ParameterDTO>> findAll(Pageable pageable) {
    log.debug("Request to get all Parameters");
    ReactiveAbstractUUIDIdentityCrudCallable<List<ParameterDTO>, ParameterDTO, Parameter, UUID>
        callable =
            context.getBean(
                ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
                "findAll",
                parameterRepository,
                parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ParameterDTO> findAllStream(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return Flux.fromIterable(
        parameterRepository.findAll(pageable).map(parameterMapper::to).getContent());
  }
  /**
   * Get one parameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ParameterDTO> findOne(UUID id) {
    log.debug("Request to get Parameter : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<ParameterDTO, ParameterDTO, Parameter, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "findById",
            id,
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the parameter by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete Parameter : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<Void, ParameterDTO, Parameter, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "delete",
            id,
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    ReactiveAbstractUUIDIdentityCrudCallable<Boolean, ParameterDTO, Parameter, UUID> callable =
        context.getBean(
            ReactiveAbstractUUIDIdentityCrudCallableImpl.class,
            "existsById",
            id,
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
