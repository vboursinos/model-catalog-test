package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterTypeDefinitionFacade
    extends ReactiveAbstractCrudFacade<ParameterTypeDefinitionDTO,UUID> {

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
  public Flux<ParameterTypeDefinitionDTO> findAll();

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
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ParameterTypeDefinition exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterTypeDefinition
   */
  public Mono<Boolean> existsById(UUID id);
}
