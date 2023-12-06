package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FloatParameterRangeService
    extends ReactiveAbstractCrudService<FloatParameterRangeDTO, FloatParameterRange> {

  /**
   * Save a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterRangeDTO> save(FloatParameterRangeDTO floatParameterRangeDTO);

  /**
   * Update a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterRangeDTO> update(FloatParameterRangeDTO floatParameterRangeDTO);

  /**
   * Partially update a floatParameterRange.
   *
   * @param floatParameterRangeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<FloatParameterRangeDTO> partialUpdate(FloatParameterRangeDTO floatParameterRangeDTO);

  /**
   * Get all the floatParameterRanges.
   *
   * @return the list of entities.
   */
  public Mono<List<FloatParameterRangeDTO>> findAll();

  public Flux<FloatParameterRangeDTO> findAllStream();

  /**
   * Get one floatParameterRange by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<FloatParameterRangeDTO> findOne(UUID id);

  /**
   * Delete the floatParameterRange by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  /**
   * Check if the floatParameterRange by id exists.
   *
   * @param id the id of the entity.
   */
  public Mono<Boolean> existsById(UUID id);
}
