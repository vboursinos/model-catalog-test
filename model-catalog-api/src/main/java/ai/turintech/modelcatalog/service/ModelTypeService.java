package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelTypeService extends ReactiveAbstractCrudService<ModelTypeDTO, ModelType, UUID> {
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
  public Mono<List<ModelTypeDTO>> findAll();

  public Flux<ModelTypeDTO> findAllStream();

  /**
   * Get one modelType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelTypeDTO> findOne(UUID id);

  /**
   * Delete the modelType by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
