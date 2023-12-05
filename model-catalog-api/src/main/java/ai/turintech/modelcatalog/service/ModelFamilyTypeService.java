package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelFamilyTypeService
    extends ReactiveAbstractCrudService<ModelFamilyTypeDTO, ModelFamilyType, UUID> {
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
  public Mono<List<ModelFamilyTypeDTO>> findAll();

  public Flux<ModelFamilyTypeDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
