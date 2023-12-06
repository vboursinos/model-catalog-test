package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.ReactiveAbstractCrudService;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MetricService extends ReactiveAbstractCrudService<MetricDTO, Metric> {

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
  public Mono<List<MetricDTO>> findAll();

  public Flux<MetricDTO> findAllStream();

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
   */
  public Mono<Void> delete(UUID id);

  public Mono<Boolean> existsById(UUID id);
}
