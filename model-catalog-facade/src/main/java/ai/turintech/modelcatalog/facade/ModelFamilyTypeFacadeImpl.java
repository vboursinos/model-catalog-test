package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.service.ModelFamilyTypeService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ModelFamilyType}. */
@Service
@Transactional
public class ModelFamilyTypeFacadeImpl implements ModelFamilyTypeFacade {

  private final Logger log = LoggerFactory.getLogger(ModelFamilyTypeFacadeImpl.class);

  @Autowired private ModelFamilyTypeService modelFamilyTypeService;

  /**
   * Save a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> save(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to save ModelFamilyType : {}", modelFamilyTypeDTO);
    return modelFamilyTypeService.save(modelFamilyTypeDTO);
  }

  /**
   * Update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> update(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to update ModelFamilyType : {}", modelFamilyTypeDTO);
    return modelFamilyTypeService.update(modelFamilyTypeDTO);
  }

  /**
   * Partially update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelFamilyTypeDTO> partialUpdate(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to partially update ModelFamilyType : {}", modelFamilyTypeDTO);
    return modelFamilyTypeService.partialUpdate(modelFamilyTypeDTO);
  }

  /**
   * Get all the modelFamilyTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ModelFamilyTypeDTO> findAll() {
    log.debug("Request to get all ModelFamilyTypes");
    return modelFamilyTypeService.findAllStream();
  }

  /**
   * Get one modelFamilyType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelFamilyTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelFamilyType : {}", id);
    return modelFamilyTypeService.findOne(id);
  }

  /**
   * Delete the modelFamilyType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelFamilyType : {}", id);
    return modelFamilyTypeService.delete(id);
  }

  /**
   * Returns whether or not a ModelFamilyType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelFamilyType
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ModelFamilyType : {}", id);
    return this.modelFamilyTypeService.existsById(id);
  }
}
