package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterRangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ai.turintech.catalog.domain.FloatParameterRange}.
 */
@Service
@Transactional
public class FloatParameterRangeService {

    private final Logger log = LoggerFactory.getLogger(FloatParameterRangeService.class);

    private final FloatParameterRangeRepository floatParameterRangeRepository;

    private final FloatParameterRangeMapper floatParameterRangeMapper;

    public FloatParameterRangeService(
        FloatParameterRangeRepository floatParameterRangeRepository,
        FloatParameterRangeMapper floatParameterRangeMapper
    ) {
        this.floatParameterRangeRepository = floatParameterRangeRepository;
        this.floatParameterRangeMapper = floatParameterRangeMapper;
    }

    /**
     * Save a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> save(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to save FloatParameterRange : {}", floatParameterRangeDTO);
        return floatParameterRangeRepository
            .save(floatParameterRangeMapper.toEntity(floatParameterRangeDTO))
            .map(floatParameterRangeMapper::toDto);
    }

    /**
     * Update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> update(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to update FloatParameterRange : {}", floatParameterRangeDTO);
        return floatParameterRangeRepository
            .save(floatParameterRangeMapper.toEntity(floatParameterRangeDTO))
            .map(floatParameterRangeMapper::toDto);
    }

    /**
     * Partially update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<FloatParameterRangeDTO> partialUpdate(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to partially update FloatParameterRange : {}", floatParameterRangeDTO);

        return floatParameterRangeRepository
            .findById(floatParameterRangeDTO.getId())
            .map(existingFloatParameterRange -> {
                floatParameterRangeMapper.partialUpdate(existingFloatParameterRange, floatParameterRangeDTO);

                return existingFloatParameterRange;
            })
            .flatMap(floatParameterRangeRepository::save)
            .map(floatParameterRangeMapper::toDto);
    }

    /**
     * Get all the floatParameterRanges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<FloatParameterRangeDTO> findAll() {
        log.debug("Request to get all FloatParameterRanges");
        return floatParameterRangeRepository.findAll().map(floatParameterRangeMapper::toDto);
    }

    /**
     * Returns the number of floatParameterRanges available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return floatParameterRangeRepository.count();
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
        return floatParameterRangeRepository.findById(id).map(floatParameterRangeMapper::toDto);
    }

    /**
     * Delete the floatParameterRange by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete FloatParameterRange : {}", id);
        return floatParameterRangeRepository.deleteById(id);
    }
    
    /**
     * Returns wether or not a FloatParameterRange exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the FloatParameterRange
     */
    public Mono<Boolean> existsById(Long id) {
    	log.debug("Request to delete FloatParameterRange : {}", id);
    	return this.floatParameterRangeRepository.existsById(id);
    }
}
