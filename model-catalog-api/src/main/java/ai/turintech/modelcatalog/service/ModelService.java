package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   * Get all the models with eager load of many-to-many relationships.
   *
   * @return the list of entities.
   */
  public Page<ModelDTO> findAllWithEagerRelationships(Pageable pageable);

  /**
   * Get one model by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelDTO> findOne(UUID id) throws Exception;

  /**
   * Delete the model by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
