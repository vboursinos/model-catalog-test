package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MlTaskTypeFacade extends ReactiveAbstractCrudFacade<MlTaskTypeDTO, UUID> {

  /**
   * Save a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<MlTaskTypeDTO> save(MlTaskTypeDTO mlTaskTypeDTO);

  /**
   * Update a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<MlTaskTypeDTO> update(MlTaskTypeDTO mlTaskTypeDTO);

  /**
   * Partially update a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<MlTaskTypeDTO> partialUpdate(MlTaskTypeDTO mlTaskTypeDTO);

  /**
   * Get all the mlTaskTypes.
   *
   * @return the list of entities.
   */
  public Flux<MlTaskTypeDTO> findAll();

  /**
   * Get one mlTaskType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<MlTaskTypeDTO> findOne(UUID id);

  /**
   * Delete the mlTaskType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);
  /**
   * Returns whether or not a MlTaskType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the MlTaskType
   */
  public Mono<Boolean> existsById(UUID id);
}
