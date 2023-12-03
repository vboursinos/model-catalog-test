package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.service.ModelEnsembleTypeService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ModelEnsembleType}. */
@Service
@Transactional
public class ModelEnsembleTypeFacadeImpl implements ModelEnsembleTypeFacade {

  private final Logger log = LoggerFactory.getLogger(ModelEnsembleTypeFacadeImpl.class);

  @Autowired
  private ModelEnsembleTypeService modelEnsembleTypeService;

  /**
   * Save a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> save(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to save ModelEnsembleType : {}", modelEnsembleTypeDTO);
    return modelEnsembleTypeService.save(modelEnsembleTypeDTO);
  }

  /**
   * Update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> update(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to update ModelEnsembleType : {}", modelEnsembleTypeDTO);
    return modelEnsembleTypeService.update(modelEnsembleTypeDTO);
  }

  /**
   * Partially update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelEnsembleTypeDTO> partialUpdate(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to partially update ModelEnsembleType : {}", modelEnsembleTypeDTO);
    return modelEnsembleTypeService.partialUpdate(modelEnsembleTypeDTO);
  }

  /**
   * Get all the modelEnsembleTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ModelEnsembleTypeDTO> findAll() {
    log.debug("Request to get all ModelEnsembleTypes");
    return modelEnsembleTypeService.findAllStream();
  }

  /**
   * Get one modelEnsembleType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelEnsembleTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelEnsembleType : {}", id);
    return modelEnsembleTypeService.findOne(id);
  }

  /**
   * Delete the modelEnsembleType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelEnsembleType : {}", id);
    return modelEnsembleTypeService.delete(id);
  }

  /**
   * Returns whether or not a ModelEnsembleTypeModelEnsembleType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelEnsembleType
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete Metric : {}", id);
    return this.modelEnsembleTypeService.existsById(id);
  }
}
