package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterTypeFacade extends ReactiveAbstractCrudFacade<ParameterTypeDTO, UUID> {

  /**
   * Save a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> save(ParameterTypeDTO parameterTypeDTO);

  /**
   * Update a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> update(ParameterTypeDTO parameterTypeDTO);

  /**
   * Partially update a parameterType.
   *
   * @param parameterTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> partialUpdate(ParameterTypeDTO parameterTypeDTO);

  /**
   * Get all the parameterTypes.
   *
   * @return the list of entities.
   */
  public Flux<ParameterTypeDTO> findAll();

  /**
   * Get one parameterType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ParameterTypeDTO> findOne(UUID id);
  /**
   * Delete the parameterType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ParameterType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterType
   */
  public Mono<Boolean> existsById(UUID id);
}
