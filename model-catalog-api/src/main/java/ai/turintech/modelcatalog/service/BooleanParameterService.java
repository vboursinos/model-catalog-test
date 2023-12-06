package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BooleanParameterService
    extends ReactiveAbstractCrudService<BooleanParameterDTO, BooleanParameter, UUID> {
  /**
   * Save a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> save(BooleanParameterDTO booleanParameterDTO);

  /**
   * Update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> update(BooleanParameterDTO booleanParameterDTO);

  /**
   * Partially update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<BooleanParameterDTO> partialUpdate(BooleanParameterDTO booleanParameterDTO);

  /**
   * Get all the booleanParameters.
   *
   * @return the list of entities.
   */
  public Mono<List<BooleanParameterDTO>> findAll();

  public Flux<BooleanParameterDTO> findAllStream();

  /**
   * Get one booleanParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<BooleanParameterDTO> findOne(UUID id);

  /**
   * Delete the booleanParameter by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
