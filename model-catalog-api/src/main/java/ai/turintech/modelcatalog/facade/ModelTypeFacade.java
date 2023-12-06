package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelTypeFacade extends ReactiveAbstractCrudFacade<ModelTypeDTO,UUID> {

  /**
   * Save a modelType.
   *
   * @param modelTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelTypeDTO> save(ModelTypeDTO modelTypeDTO);

  /**
   * Update a modelType.
   *
   * @param modelTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelTypeDTO> update(ModelTypeDTO modelTypeDTO);

  /**
   * Partially update a modelType.
   *
   * @param modelTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelTypeDTO> partialUpdate(ModelTypeDTO modelTypeDTO);

  /**
   * Get all the modelTypes.
   *
   * @return the list of entities.
   */
  public Flux<ModelTypeDTO> findAll();

  /**
   * Get one modelType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelTypeDTO> findOne(UUID id);

  /**
   * Delete the modelType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ModelType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelType
   */
  public Mono<Boolean> existsById(UUID id);
}
