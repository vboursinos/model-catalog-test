package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BooleanParameterFacade
    extends ReactiveAbstractCrudFacade<BooleanParameterDTO, UUID> {

  /**
   * Save a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> save(BooleanParameterDTO booleanParameterDTO);

  /**
   * Update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> update(BooleanParameterDTO booleanParameterDTO);

  /**
   * Partially update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> partialUpdate(BooleanParameterDTO booleanParameterDTO);

  /**
   * Get all the booleanParameters.
   *
   * @return the list of entities.
   */
  public Flux<BooleanParameterDTO> findAll();

  /**
   * Get one booleanParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<BooleanParameterDTO> findOne(UUID id);

  /**
   * Delete the booleanParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns wether or not a BooleanParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the BooleanParameter
   */
  public Mono<Boolean> existsById(UUID id);
}
