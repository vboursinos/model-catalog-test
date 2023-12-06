package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelStructureTypeService
    extends ReactiveAbstractCrudService<ModelStructureTypeDTO, ModelStructureType> {

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
  public Mono<List<ModelStructureTypeDTO>> findAll();

  public Flux<ModelStructureTypeDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
