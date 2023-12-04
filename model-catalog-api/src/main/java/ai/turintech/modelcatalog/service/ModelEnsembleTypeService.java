package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelEnsembleTypeService {

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
  public Mono<List<ModelEnsembleTypeDTO>> findAll();

  public Flux<ModelEnsembleTypeDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
