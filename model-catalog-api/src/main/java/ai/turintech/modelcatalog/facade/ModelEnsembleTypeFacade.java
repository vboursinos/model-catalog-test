package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelEnsembleTypeFacade extends ReactiveAbstractCrudFacade<ModelEnsembleTypeDTO,UUID> {

  /**
   * Save a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> save(ModelEnsembleTypeDTO modelEnsembleTypeDTO);

  /**
   * Update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> update(ModelEnsembleTypeDTO modelEnsembleTypeDTO);

  /**
   * Partially update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> partialUpdate(ModelEnsembleTypeDTO modelEnsembleTypeDTO);

  /**
   * Get all the modelEnsembleTypes.
   *
   * @return the list of entities.
   */
  public Flux<ModelEnsembleTypeDTO> findAll();

  /**
   * Get one modelEnsembleType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelEnsembleTypeDTO> findOne(UUID id);

  /**
   * Delete the modelEnsembleType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ModelEnsembleTypeModelEnsembleType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelEnsembleType
   */
  public Mono<Boolean> existsById(UUID id);
}
