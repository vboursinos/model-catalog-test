package ai.turintech.modelcatalog.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.service.FloatParameterRangeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ai.turintech.catalog.domain.FloatParameterRange}.
 */
@Service
@Transactional
public class FloatParameterRangeFacade {

    private final Logger log = LoggerFactory.getLogger(FloatParameterRangeFacade.class);

    private final FloatParameterRangeService floatParameterRangeService;


    public FloatParameterRangeFacade(
    		FloatParameterRangeService floatParameterRangeService
    ) {
        this.floatParameterRangeService = floatParameterRangeService;
    }

    /**
     * Save a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> save(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to save FloatParameterRange : {}", floatParameterRangeDTO);
        return floatParameterRangeService.save(floatParameterRangeDTO);
    }

    /**
     * Update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> update(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to update FloatParameterRange : {}", floatParameterRangeDTO);
        return floatParameterRangeService.save(floatParameterRangeDTO);
    }

    /**
     * Partially update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> partialUpdate(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to partially update FloatParameterRange : {}", floatParameterRangeDTO);

        return floatParameterRangeService.partialUpdate(floatParameterRangeDTO);
    }

    /**
     * Get all the floatParameterRanges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<FloatParameterRangeDTO> findAll() {
        log.debug("Request to get all FloatParameterRanges");
        return floatParameterRangeService.findAll();
    }

    /**
     * Returns the number of floatParameterRanges available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return floatParameterRangeService.countAll();
    }

    /**
     * Get one floatParameterRange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<FloatParameterRangeDTO> findOne(Long id) {
        log.debug("Request to get FloatParameterRange : {}", id);
        return floatParameterRangeService.findOne(id);
    }

    /**
     * Delete the floatParameterRange by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete FloatParameterRange : {}", id);
        return floatParameterRangeService.delete(id);
    }
    
    /**
     * Returns wether or not a FloatParameterRange exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the FloatParameterRange
     */
    public Mono<Boolean> existsById(Long id) {
    	log.debug("Request to delete FloatParameterRange : {}", id);
    	return this.floatParameterRangeService.existsById(id);
    }
}
