package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MlTaskTypeService {

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
  public Mono<List<MlTaskTypeDTO>> findAll();

  public Flux<MlTaskTypeDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
