package ai.turintech.modelcatalog.facade;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.service.MlTaskTypeService;
import ai.turintech.modelcatalog.entity.MlTaskType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link MlTaskType}.
 */
@Service
@Transactional
public class MlTaskTypeFacade {

    private final Logger log = LoggerFactory.getLogger(MlTaskTypeFacade.class);

    private final MlTaskTypeService modelTaskTypeService;

    public MlTaskTypeFacade(MlTaskTypeService modelTaskTypeService) {
        this.modelTaskTypeService = modelTaskTypeService;
    }

    /**
     * Save a mlTaskType.
     *
     * @param mlTaskTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MlTaskTypeDTO> save(MlTaskTypeDTO mlTaskTypeDTO) {
        log.debug("Request to save MlTaskType : {}", mlTaskTypeDTO);
        return modelTaskTypeService.save(mlTaskTypeDTO);
    }

    /**
     * Update a mlTaskType.
     *
     * @param mlTaskTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MlTaskTypeDTO> update(MlTaskTypeDTO mlTaskTypeDTO) {
        log.debug("Request to update MlTaskType : {}", mlTaskTypeDTO);
        return modelTaskTypeService.save(mlTaskTypeDTO);
    }

    /**
     * Partially update a mlTaskType.
     *
     * @param mlTaskTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<MlTaskTypeDTO> partialUpdate(MlTaskTypeDTO mlTaskTypeDTO) {
        log.debug("Request to partially update MlTaskType : {}", mlTaskTypeDTO);

        return modelTaskTypeService.partialUpdate(mlTaskTypeDTO);
    }

    /**
     * Get all the mlTaskTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<MlTaskTypeDTO> findAll() {
        log.debug("Request to get all MlTaskTypes");
        return modelTaskTypeService.findAllStream();
    }

    /**
     * Get one mlTaskType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<MlTaskTypeDTO> findOne(UUID id) {
        log.debug("Request to get MlTaskType : {}", id);
        return modelTaskTypeService.findOne(id);
    }

    /**
     * Delete the mlTaskType by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete MlTaskType : {}", id);
        return modelTaskTypeService.delete(id);
    }
    
    /**
     * Returns whether or not a MlTaskType exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the MlTaskType
     */
    public Mono<Boolean> existsById(UUID id) {
    	log.debug("Request to delete Metric : {}", id);
    	return this.modelTaskTypeService.existsById(id);
    }
}
