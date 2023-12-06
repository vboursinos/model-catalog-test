package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelStructureTypeFacade
    extends ReactiveAbstractCrudFacade<ModelStructureTypeDTO> {

  /**
   * Save a modelStructureType.
   *
   * @param modelStructureTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelStructureTypeDTO> save(ModelStructureTypeDTO modelStructureTypeDTO);

  /**
   * Update a modelStructureType.
   *
   * @param modelStructureTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelStructureTypeDTO> update(ModelStructureTypeDTO modelStructureTypeDTO);

  /**
   * Partially update a modelStructureType.
   *
   * @param modelStructureTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelStructureTypeDTO> partialUpdate(ModelStructureTypeDTO modelStructureTypeDTO);

  /**
   * Get all the modelStructureTypes.
   *
   * @return the list of entities.
   */
  public Flux<ModelStructureTypeDTO> findAll();
  /**
   * Get one modelStructureType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelStructureTypeDTO> findOne(UUID id);

  /**
   * Delete the modelStructureType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ModelStructureType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelStructureType
   */
  public Mono<Boolean> existsById(UUID id);
}
