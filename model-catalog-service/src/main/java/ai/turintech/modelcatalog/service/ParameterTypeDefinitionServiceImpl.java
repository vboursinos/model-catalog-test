package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterTypeDefinitionMapper;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.repository.ParameterTypeDefinitionRepository;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/** Service Implementation for managing {@link ParameterTypeDefinition}. */
@Service
@Transactional
public class ParameterTypeDefinitionServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        ParameterTypeDefinitionDTO, ParameterTypeDefinition>
    implements ParameterTypeDefinitionService {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeDefinitionServiceImpl.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;

  @Autowired private ParameterTypeDefinitionRepository parameterTypeDefinitionRepository;

  @Autowired private ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAllStream() {
    log.debug("Request to get all ParameterTypeDefinitions");
    return Flux.fromIterable(
            parameterTypeDefinitionRepository.findAll().stream()
                .map(parameterTypeDefinitionMapper::to)
                .collect(Collectors.toCollection(LinkedList::new)))
        .subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameterTypeDefinitions where IntegerParameter is {@code null}.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAllWhereIntegerParameterIsNull() {
    log.debug("Request to get all parameterTypeDefinitions where IntegerParameter is null");
    return Flux.fromIterable(
            StreamSupport.stream(parameterTypeDefinitionRepository.findAll().spliterator(), false)
                .filter(
                    parameterTypeDefinition ->
                        parameterTypeDefinition.getIntegerParameter() == null)
                .map(parameterTypeDefinitionMapper::to)
                .collect(Collectors.toCollection(LinkedList::new)))
        .subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameterTypeDefinitions where FloatParameter is {@code null}.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAllWhereFloatParameterIsNull() {
    log.debug("Request to get all parameterTypeDefinitions where FloatParameter is null");
    return Flux.fromIterable(
            StreamSupport.stream(parameterTypeDefinitionRepository.findAll().spliterator(), false)
                .filter(
                    parameterTypeDefinition -> parameterTypeDefinition.getFloatParameter() == null)
                .map(parameterTypeDefinitionMapper::to)
                .collect(Collectors.toCollection(LinkedList::new)))
        .subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameterTypeDefinitions where CategoricalParameter is {@code null}.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAllWhereCategoricalParameterIsNull() {
    log.debug("Request to get all parameterTypeDefinitions where CategoricalParameter is null");
    return Flux.fromIterable(
            StreamSupport.stream(parameterTypeDefinitionRepository.findAll().spliterator(), false)
                .filter(
                    parameterTypeDefinition ->
                        parameterTypeDefinition.getCategoricalParameter() == null)
                .map(parameterTypeDefinitionMapper::to)
                .collect(Collectors.toCollection(LinkedList::new)))
        .subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameterTypeDefinitions where BooleanParameter is {@code null}.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAllWhereBooleanParameterIsNull() {
    log.debug("Request to get all parameterTypeDefinitions where BooleanParameter is null");

    return Flux.fromStream(
            parameterTypeDefinitionRepository.findAll().stream()
                .filter(
                    parameterTypeDefinition ->
                        parameterTypeDefinition.getBooleanParameter() == null)
                .map(parameterTypeDefinitionMapper::to))
        .subscribeOn(Schedulers.boundedElastic());
  }
}
