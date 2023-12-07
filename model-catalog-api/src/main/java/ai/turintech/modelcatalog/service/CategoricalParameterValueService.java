package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoricalParameterValueService
    extends ReactiveAbstractCrudService<
        CategoricalParameterValueDTO, CategoricalParameterValue, UUID> {

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
  public Mono<List<CategoricalParameterValueDTO>> findAll();

  public Flux<CategoricalParameterValueDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
