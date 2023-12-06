package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoricalParameterValueFacade
    extends ReactiveAbstractCrudFacade<CategoricalParameterValueDTO> {
  /**
   * Save a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterValueDTO> save(
      CategoricalParameterValueDTO categoricalParameterValueDTO);
  /**
   * Update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterValueDTO> update(
      CategoricalParameterValueDTO categoricalParameterValueDTO);

  /**
   * Partially update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterValueDTO> partialUpdate(
      CategoricalParameterValueDTO categoricalParameterValueDTO);

  /**
   * Get all the categoricalParameterValues.
   *
   * @return the list of entities.
   */
  public Flux<CategoricalParameterValueDTO> findAll();

  /**
   * Get one categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<CategoricalParameterValueDTO> findOne(UUID id);

  /**
   * Delete the categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns wether or not a CategoricalParameterValue exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the categoricalParameterValue
   */
  public Mono<Boolean> existsById(UUID id);
}
