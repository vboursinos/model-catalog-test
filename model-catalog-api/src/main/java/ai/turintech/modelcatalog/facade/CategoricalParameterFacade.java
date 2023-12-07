package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoricalParameterFacade
    extends ReactiveAbstractCrudFacade<CategoricalParameterDTO, UUID> {

  /**
   * Save a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> save(CategoricalParameterDTO categoricalParameterDTO);

  /**
   * Update a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> update(CategoricalParameterDTO categoricalParameterDTO);

  /**
   * Partially update a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> partialUpdate(
      CategoricalParameterDTO categoricalParameterDTO);

  /**
   * Get all the categoricalParameters.
   *
   * @return the list of entities.
   */
  public Flux<CategoricalParameterDTO> findAll();

  /**
   * Get one categoricalParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<CategoricalParameterDTO> findOne(UUID id);

  /**
   * Delete the categoricalParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns wether or not a BooleanParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the CategoricalParameter
   */
  public Mono<Boolean> existsById(UUID id);
}
