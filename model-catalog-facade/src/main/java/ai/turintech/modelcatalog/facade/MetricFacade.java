package ai.turintech.modelcatalog.facade;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.service.MetricService;
import ai.turintech.modelcatalog.entity.Metric;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Metric}.
 */
@Service
@Transactional
public class MetricFacade {

    private final Logger log = LoggerFactory.getLogger(MetricFacade.class);

    private final MetricService metricService;


    public MetricFacade(MetricService metricService) {
        this.metricService = metricService;
    }

    /**
     * Save a metric.
     *
     * @param metricDTO the entity to save.
     * @return the persisted entity.
     */
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
    public Mono<MetricDTO> partialUpdate(MetricDTO metricDTO) {
        log.debug("Request to partially update Metric : {}", metricDTO);

        return metricService.partialUpdate(metricDTO);
    }

    /**
     * Get all the metrics.
     *
     * @return the list of entities.
     */
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
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Metric : {}", id);
        return metricService.delete(id);
    }
    
    /**
     * Returns whether or not a Metric exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the Metric
     */
    public Mono<Boolean> existsById(UUID id) {
    	log.debug("Request to delete Metric : {}", id);
    	return this.metricService.existsById(id);
    }
}
