package ai.turintech.modelcatalog.facade;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.service.ModelTypeService;
import ai.turintech.modelcatalog.entity.ModelType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ModelType}.
 */
@Service
@Transactional
public class ModelTypeFacade {

    private final Logger log = LoggerFactory.getLogger(ModelTypeFacade.class);

    private final ModelTypeService modelTypeService;

    public ModelTypeFacade(ModelTypeService modelTypeService) {
        this.modelTypeService = modelTypeService;
    }

    /**
     * Save a modelType.
     *
     * @param modelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ModelTypeDTO> save(ModelTypeDTO modelTypeDTO) {
        log.debug("Request to save ModelType : {}", modelTypeDTO);
        return modelTypeService.save(modelTypeDTO);
    }

    /**
     * Update a modelType.
     *
     * @param modelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ModelTypeDTO> update(ModelTypeDTO modelTypeDTO) {
        log.debug("Request to update ModelType : {}", modelTypeDTO);
        return modelTypeService.update(modelTypeDTO);
    }

    /**
     * Partially update a modelType.
     *
     * @param modelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ModelTypeDTO> partialUpdate(ModelTypeDTO modelTypeDTO) {
        log.debug("Request to partially update ModelType : {}", modelTypeDTO);

        return modelTypeService.partialUpdate(modelTypeDTO);
    }

    /**
     * Get all the modelTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ModelTypeDTO> findAll() {
        log.debug("Request to get all ModelTypes");
        return modelTypeService.findAllStream();
    }

    /**
     * Get one modelType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ModelTypeDTO> findOne(UUID id) {
        log.debug("Request to get ModelType : {}", id);
        return modelTypeService.findOne(id);
    }

    /**
     * Delete the modelType by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete ModelType : {}", id);
        return modelTypeService.delete(id);
    }
    
    /**
     * Returns whether or not a ModelType exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the ModelType
     */
    public Mono<Boolean> existsById(UUID id) {
    	log.debug("Request to delete ModelGroupType : {}", id);
    	return this.modelTypeService.existsById(id);
    }
}
