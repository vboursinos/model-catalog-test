package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelGroupTypeFacade extends ReactiveAbstractCrudFacade<ModelGroupTypeDTO, UUID> {

  /**
   * Save a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> save(ModelGroupTypeDTO modelGroupTypeDTO);

  /**
   * Update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> update(ModelGroupTypeDTO modelGroupTypeDTO);

  /**
   * Partially update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> partialUpdate(ModelGroupTypeDTO modelGroupTypeDTO);

  /**
   * Get all the modelGroupTypes.
   *
   * @return the list of entities.
   */
  public Flux<ModelGroupTypeDTO> findAll();

  /**
   * Get one modelGroupType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelGroupTypeDTO> findOne(UUID id);

  /**
   * Delete the modelGroupType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ModelGroupType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelGroupType
   */
  public Mono<Boolean> existsById(UUID id);
}
