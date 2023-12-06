package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterTypeDefinitionService
    extends ReactiveAbstractCrudService<ParameterTypeDefinitionDTO, ParameterTypeDefinition> {

  /**
   * Save a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDefinitionDTO> save(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO);

  /**
   * Update a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterTypeDefinitionDTO> update(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO);

  /**
   * Partially update a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDefinitionDTO> partialUpdate(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO);

  /**
   * Get all the parameterTypeDefinitions.
   *
   * @return the list of entities.
   */
  public Mono<List<ParameterTypeDefinitionDTO>> findAll();

  public Flux<ParameterTypeDefinitionDTO> findAllStream();

  /**
   * Get all the parameterTypeDefinitions where IntegerParameter is {@code null}.
   *
   * @return the list of entities.
   */
  public Flux<ParameterTypeDefinitionDTO> findAllWhereIntegerParameterIsNull();

  /**
   * Get all the parameterTypeDefinitions where FloatParameter is {@code null}.
   *
   * @return the list of entities.
   */
  public Flux<ParameterTypeDefinitionDTO> findAllWhereFloatParameterIsNull();

  /**
   * Get all the parameterTypeDefinitions where CategoricalParameter is {@code null}.
   *
   * @return the list of entities.
   */
  public Flux<ParameterTypeDefinitionDTO> findAllWhereCategoricalParameterIsNull();

  /**
   * Get all the parameterTypeDefinitions where BooleanParameter is {@code null}.
   *
   * @return the list of entities.
   */
  public Flux<ParameterTypeDefinitionDTO> findAllWhereBooleanParameterIsNull();

  /**
   * Get one parameterTypeDefinition by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ParameterTypeDefinitionDTO> findOne(UUID id);

  /**
   * Delete the parameterTypeDefinition by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
