package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelFamilyTypeFacade
    extends ReactiveAbstractCrudFacade<ModelFamilyTypeDTO, UUID> {

  /**
   * Save a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> save(ModelFamilyTypeDTO modelFamilyTypeDTO);

  /**
   * Update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> update(ModelFamilyTypeDTO modelFamilyTypeDTO);

  /**
   * Partially update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> partialUpdate(ModelFamilyTypeDTO modelFamilyTypeDTO);

  /**
   * Get all the modelFamilyTypes.
   *
   * @return the list of entities.
   */
  public Flux<ModelFamilyTypeDTO> findAll();

  /**
   * Get one modelFamilyType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelFamilyTypeDTO> findOne(UUID id);

  /**
   * Delete the modelFamilyType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ModelFamilyType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelFamilyType
   */
  public Mono<Boolean> existsById(UUID id);
}
