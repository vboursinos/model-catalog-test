package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MetricFacade extends ReactiveAbstractCrudFacade<MetricDTO, UUID> {
  /**
   * Save a metric.
   *
   * @param metricDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<MetricDTO> save(MetricDTO metricDTO);

  /**
   * Update a metric.
   *
   * @param metricDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<MetricDTO> update(MetricDTO metricDTO);

  /**
   * Partially update a metric.
   *
   * @param metricDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<MetricDTO> partialUpdate(MetricDTO metricDTO);

  /**
   * Get all the metrics.
   *
   * @return the list of entities.
   */
  public Flux<MetricDTO> findAll();

  /**
   * Get one metric by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  public Mono<MetricDTO> findOne(UUID id);

  /**
   * Delete the metric by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id);

  /**
   * Returns whether or not a Metric exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the Metric
   */
  public Mono<Boolean> existsById(UUID id);
}
