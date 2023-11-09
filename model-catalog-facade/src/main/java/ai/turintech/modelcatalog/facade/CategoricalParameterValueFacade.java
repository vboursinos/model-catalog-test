package ai.turintech.modelcatalog.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.service.CategoricalParameterValueService;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service Implementation for managing {@link CategoricalParameterValue}.
 */
@Service
@Transactional
public class CategoricalParameterValueFacade {

    private final Logger log = LoggerFactory.getLogger(CategoricalParameterValueFacade.class);

    private final CategoricalParameterValueService categoricalParameterValueService;

    public CategoricalParameterValueFacade(
        CategoricalParameterValueService categoricalParameterValueService) {
        this.categoricalParameterValueService = categoricalParameterValueService;
    }

    /**
     * Save a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CategoricalParameterValueDTO> save(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to save CategoricalParameterValue : {}", categoricalParameterValueDTO);
        return categoricalParameterValueService.save(categoricalParameterValueDTO);
    }

    /**
     * Update a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CategoricalParameterValueDTO> update(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to update CategoricalParameterValue : {}", categoricalParameterValueDTO);
        return categoricalParameterValueService.save(categoricalParameterValueDTO);
    }

    /**
     * Partially update a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<CategoricalParameterValueDTO> partialUpdate(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to partially update CategoricalParameterValue : {}", categoricalParameterValueDTO);

        return categoricalParameterValueService.partialUpdate(categoricalParameterValueDTO);
    }

    /**
     * Get all the categoricalParameterValues.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<CategoricalParameterValueDTO> findAll() {
        log.debug("Request to get all CategoricalParameterValues");
        return categoricalParameterValueService.findAllStream();
    }

    /**
     * Get one categoricalParameterValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CategoricalParameterValueDTO> findOne(UUID id) {
        log.debug("Request to get CategoricalParameterValue : {}", id);
        return categoricalParameterValueService.findOne(id);
    }

    /**
     * Delete the categoricalParameterValue by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete CategoricalParameterValue : {}", id);
        return categoricalParameterValueService.delete(id);
    }
    
    /**
     * Returns wether or not a CategoricalParameterValue exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the categoricalParameterValue
     */
//    public Mono<Boolean> existsById(Long id) {
//    	log.debug("Request to delete CategoricalParameterValue : {}", id);
//    	return this.categoricalParameterValueService.existsById(id);
//    }
}
