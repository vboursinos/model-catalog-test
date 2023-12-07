package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FloatParameterRangeFacade
    extends ReactiveAbstractCrudFacade<FloatParameterRangeDTO, UUID> {

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
  public Flux<FloatParameterRangeDTO> findAll();

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
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);
  /**
   * Returns wether or not a FloatParameterRange exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the FloatParameterRange
   */
  public Mono<Boolean> existsById(UUID id);
}
