package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegerParameterValueService
    extends ReactiveAbstractCrudService<IntegerParameterValueDTO, IntegerParameterValue, UUID> {
  /**
   * Save a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> save(IntegerParameterValueDTO integerParameterValueDTO);

  /**
   * Update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> update(IntegerParameterValueDTO integerParameterValueDTO);

  /**
   * Partially update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterValueDTO> partialUpdate(
      IntegerParameterValueDTO integerParameterValueDTO);

  /**
   * Get all the integerParameterValues.
   *
   * @return the list of entities.
   */
  public Mono<List<IntegerParameterValueDTO>> findAll();

  public Flux<IntegerParameterValueDTO> findAllStream();

  /**
   * Get one integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<IntegerParameterValueDTO> findOne(UUID id);

  /**
   * Delete the integerParameterValue by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
