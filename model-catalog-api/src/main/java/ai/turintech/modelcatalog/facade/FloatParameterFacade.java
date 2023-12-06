package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FloatParameterFacade extends ReactiveAbstractCrudFacade<FloatParameterDTO,UUID> {

  /**
   * Save a floatParameter.
   *
   * @param floatParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> save(FloatParameterDTO floatParameterDTO);
  /**
   * Update a floatParameter.
   *
   * @param floatParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> update(FloatParameterDTO floatParameterDTO);

  /**
   * Partially update a floatParameter.
   *
   * @param floatParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> partialUpdate(FloatParameterDTO floatParameterDTO);
  /**
   * Get all the floatParameters.
   *
   * @return the list of entities.
   */
  public Flux<FloatParameterDTO> findAll();

  /**
   * Get one floatParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<FloatParameterDTO> findOne(UUID id);

  /**
   * Delete the floatParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns wether or not a FloatParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the FloatParameter
   */
  public Mono<Boolean> existsById(UUID id);
}
