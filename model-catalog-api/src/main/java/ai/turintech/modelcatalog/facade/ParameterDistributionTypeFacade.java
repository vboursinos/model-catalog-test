package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterDistributionTypeFacade
    extends ReactiveAbstractCrudFacade<ParameterDistributionTypeDTO,UUID> {

  /**
   * Save a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterDistributionTypeDTO> save(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO);

  /**
   * Update a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterDistributionTypeDTO> update(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO);

  /**
   * Partially update a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ParameterDistributionTypeDTO> partialUpdate(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO);

  /**
   * Get all the parameterDistributionTypes.
   *
   * @return the list of entities.
   */
  public Flux<ParameterDistributionTypeDTO> findAll();

  /**
   * Get one parameterDistributionType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<ParameterDistributionTypeDTO> findOne(UUID id);

  /**
   * Delete the parameterDistributionType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a ParameterDistributionType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterDistributionType
   */
  public Mono<Boolean> existsById(UUID id);
}
