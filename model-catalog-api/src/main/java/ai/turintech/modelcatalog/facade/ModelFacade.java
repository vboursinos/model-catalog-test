package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import ai.turintech.modelcatalog.exceptions.FindOneException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ModelFacade {

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
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  public Mono<ModelPaginatedListDTO> findAll(Pageable pageable);

  /**
   * Get one model by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ModelDTO> findOne(UUID id) throws FindOneException;

  /**
   * Delete the model by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a Model exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the Model
   */
  public Mono<Boolean> existsById(UUID id);
}
