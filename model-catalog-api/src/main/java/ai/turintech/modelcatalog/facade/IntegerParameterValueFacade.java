package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegerParameterValueFacade
    extends ReactiveAbstractCrudFacade<IntegerParameterValueDTO, UUID> {

  /**
   * Save a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> save(IntegerParameterValueDTO integerParameterValueDTO);

  /**
   * Update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> update(IntegerParameterValueDTO integerParameterValueDTO);
  /**
   * Partially update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> partialUpdate(
      IntegerParameterValueDTO integerParameterValueDTO);

  /**
   * Get all the integerParameterValues.
   *
   * @return the list of entities.
   */
  public Flux<IntegerParameterValueDTO> findAll();

  /**
   * Get one integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<IntegerParameterValueDTO> findOne(UUID id);

  /**
   * Delete the integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a IntegerParameterValue exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the IntegerParameterValue
   */
  public Mono<Boolean> existsById(UUID id);
}
