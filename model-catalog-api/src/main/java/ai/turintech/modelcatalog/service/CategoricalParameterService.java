package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoricalParameterService
    extends ReactiveAbstractUUIDIdentityCrudService<
        CategoricalParameterDTO, CategoricalParameter, UUID> {

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
  public Mono<List<CategoricalParameterDTO>> findAll();

  public Flux<CategoricalParameterDTO> findAllStream();
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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
