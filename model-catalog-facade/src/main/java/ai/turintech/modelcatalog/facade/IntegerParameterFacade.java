package ai.turintech.modelcatalog.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.service.IntegerParameterService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ai.turintech.catalog.domain.IntegerParameter}.
 */
@Service
@Transactional
public class IntegerParameterFacade {

    private final Logger log = LoggerFactory.getLogger(IntegerParameterFacade.class);

    private final IntegerParameterService integerParameterService;

    public IntegerParameterFacade(IntegerParameterService integerParameterService) {
        this.integerParameterService = integerParameterService;
    }

    /**
     * Save a integerParameter.
     *
     * @param integerParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to save IntegerParameter : {}", integerParameterDTO);
        return integerParameterService.save(integerParameterDTO);
    }

    /**
     * Update a integerParameter.
     *
     * @param integerParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to update IntegerParameter : {}", integerParameterDTO);
        return integerParameterService.save(integerParameterDTO);
    }

    /**
     * Partially update a integerParameter.
     *
     * @param integerParameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to partially update IntegerParameter : {}", integerParameterDTO);

        return integerParameterService.partialUpdate(integerParameterDTO);
    }

    /**
     * Get all the integerParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IntegerParameterDTO> findAll() {
        log.debug("Request to get all IntegerParameters");
        return integerParameterService.findAll();
    }

    /**
     * Returns the number of integerParameters available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return integerParameterService.countAll();
    }

    /**
     * Get one integerParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IntegerParameterDTO> findOne(Long id) {
        log.debug("Request to get IntegerParameter : {}", id);
        return integerParameterService.findOne(id);
    }

    /**
     * Delete the integerParameter by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IntegerParameter : {}", id);
        return integerParameterService.delete(id);
    }
    
    /**
     * Returns wether or not a IntegerParameter exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the IntegerParameter
     */
    public Mono<Boolean> existsById(Long id) {
    	log.debug("Request to delete IntegerParameter : {}", id);
    	return this.integerParameterService.existsById(id);
    }
}
