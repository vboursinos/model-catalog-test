package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterTypeService
    extends ReactiveAbstractCrudService<ParameterTypeDTO, ParameterType, UUID> {
  /**
   * Save a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> save(ParameterTypeDTO parameterTypeDTO);

  /**
   * Update a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> update(ParameterTypeDTO parameterTypeDTO);

  /**
   * Partially update a parameterType.
   *
   * @param parameterTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDTO> partialUpdate(ParameterTypeDTO parameterTypeDTO);

  /**
   * Get all the parameterTypes.
   *
   * @return the list of entities.
   */
  public Mono<List<ParameterTypeDTO>> findAll();

  public Flux<ParameterTypeDTO> findAllStream();

  /**
   * Get one parameterType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ParameterTypeDTO> findOne(UUID id);

  /**
   * Delete the parameterType by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
