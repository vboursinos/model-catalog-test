package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelGroupTypeService
    extends ReactiveAbstractCrudService<ModelGroupTypeDTO, ModelGroupType> {
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
  public Mono<List<ModelGroupTypeDTO>> findAll();

  public Flux<ModelGroupTypeDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
