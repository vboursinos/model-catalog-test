package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelService {

  /**
   * Save a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelDTO> save(ModelDTO modelDTO);

  /**
   * Update a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelDTO> update(ModelDTO modelDTO);

  /**
   * Partially update a model.
   *
   * @param modelDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelDTO> partialUpdate(ModelDTO modelDTO);

  /**
   * Get all the models.
   *
   * @return the list of entities.
   */
  public Flux<ModelDTO> findAll();

  /**
   * Get one model by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelDTO> findOne(UUID id);

  /**
   * Delete the model by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
