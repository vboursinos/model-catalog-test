package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.service.MetricService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link Metric}. */
@Service
@Transactional
public class MetricFacadeImpl extends ReactiveAbstractCrudFacadeImpl<MetricDTO, Metric, UUID>
    implements MetricFacade {

  private final Logger log = LoggerFactory.getLogger(MetricFacadeImpl.class);

  @Autowired private MetricService metricService;

  /**
   * Save a metric.
   *
   * @param metricDTO the entity to save.
   * @return the persisted entity.
   */
  @Autowired
  public Mono<MetricDTO> save(MetricDTO metricDTO) {
    log.debug("Request to save Metric : {}", metricDTO);
    return metricService.save(metricDTO);
  }

  /**
   * Update a metric.
   *
   * @param metricDTO the entity to save.
   * @return the persisted entity.
   */
  @Autowired
  public Mono<MetricDTO> update(MetricDTO metricDTO) {
    log.debug("Request to update Metric : {}", metricDTO);
    return metricService.save(metricDTO);
  }

  /**
   * Partially update a metric.
   *
   * @param metricDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Autowired
  public Mono<MetricDTO> partialUpdate(MetricDTO metricDTO) {
    log.debug("Request to partially update Metric : {}", metricDTO);

    return metricService.partialUpdate(metricDTO);
  }

  /**
   * Get all the metrics.
   *
   * @return the list of entities.
   */
  @Autowired
  @Transactional(readOnly = true)
  public Flux<MetricDTO> findAll() {
    log.debug("Request to get all Metrics");
    return metricService.findAllStream();
  }

  /**
   * Get one metric by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Autowired
  @Transactional(readOnly = true)
  public Mono<MetricDTO> findOne(UUID id) {
    log.debug("Request to get Metric : {}", id);
    return metricService.findOne(id);
  }

  /**
   * Delete the metric by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Autowired
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete Metric : {}", id);
    return metricService.delete(id);
  }

  /**
   * Returns whether or not a Metric exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the Metric
   */
  @Autowired
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete Metric : {}", id);
    return this.metricService.existsById(id);
  }
}
