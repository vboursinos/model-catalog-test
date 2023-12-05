package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegerParameterService
    extends ReactiveAbstractCrudService<IntegerParameterDTO, IntegerParameter, UUID> {

  /**
   * Save a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO);

  /**
   * Update a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO);

  /**
   * Partially update a integerParameter.
   *
   * @param integerParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO);

  /**
   * Get all the integerParameters.
   *
   * @return the list of entities.
   */
  public Mono<List<IntegerParameterDTO>> findAll();

  public Flux<IntegerParameterDTO> findAllStream();

  /**
   * Get one integerParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<IntegerParameterDTO> findOne(UUID id);

  /**
   * Delete the integerParameter by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  /**
   * Search for the integerParameter corresponding to the query.
   *
   * @param id the id of the integerParameter.
   * @return boolean true if value exists.
   */
  public Mono<Boolean> existsById(UUID id);
}
