package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegerParameterFacade extends ReactiveAbstractCrudFacade<IntegerParameterDTO, UUID> {
  /**
   * Save a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO);

  /**
   * Update a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO);

  /**
   * Partially update a integerParameter.
   *
   * @param integerParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO);

  /**
   * Get all the integerParameters.
   *
   * @return the list of entities.
   */
  public Flux<IntegerParameterDTO> findAll();

  /**
   * Get one integerParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<IntegerParameterDTO> findOne(UUID id);

  /**
   * Delete the integerParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);
  /**
   * Returns wether or not a IntegerParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the IntegerParameter
   */
  public Mono<Boolean> existsById(UUID id);
}
