package ai.turintech.modelcatalog.facade;

import java.util.UUID;

import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.service.ModelService;
import ai.turintech.modelcatalog.entity.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelFacade {

    private final Logger log = LoggerFactory.getLogger(ModelFacade.class);

    private final ModelService modelService;

    public ModelFacade(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ModelDTO> save(ModelDTO modelDTO) {
        log.debug("Request to save Model : {}", modelDTO);
        return modelService.save(modelDTO);
    }

    /**
     * Update a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ModelDTO> update(ModelDTO modelDTO) {
        log.debug("Request to update Model : {}", modelDTO);
        return modelService.save(modelDTO);
    }

    /**
     * Partially update a model.
     *
     * @param modelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ModelDTO> partialUpdate(ModelDTO modelDTO) {
        log.debug("Request to partially update Model : {}", modelDTO);

        return modelService.partialUpdate(modelDTO);
    }

    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<ModelPaginatedListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Models");
        return modelService.findAll(pageable);
    }

    /**
     * Get one model by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ModelDTO> findOne(UUID id) throws Exception {
        log.debug("Request to get Model : {}", id);
        return modelService.findOne(id);
    }

    /**
     * Delete the model by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Model : {}", id);
        return modelService.delete(id);
    }
    
    /**
     * Returns whether or not a Model exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the Model
     */
    public Mono<Boolean> existsById(UUID id) {
    	log.debug("Request to delete ModelGroupType : {}", id);
    	return this.modelService.existsById(id);
    }
}
