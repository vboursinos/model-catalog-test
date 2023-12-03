package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.service.ModelGroupTypeService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ModelGroupType}. */
@Service
@Transactional
public class ModelGroupTypeFacadeImpl implements ModelGroupTypeFacade {

  private final Logger log = LoggerFactory.getLogger(ModelGroupTypeFacadeImpl.class);

  @Autowired
  private ModelGroupTypeService modelGroupTypeService;

  /**
   * Save a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> save(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to save ModelGroupType : {}", modelGroupTypeDTO);
    return modelGroupTypeService.save(modelGroupTypeDTO);
  }

  /**
   * Update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> update(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to update ModelGroupType : {}", modelGroupTypeDTO);
    return modelGroupTypeService.update(modelGroupTypeDTO);
  }

  /**
   * Partially update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ModelGroupTypeDTO> partialUpdate(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to partially update ModelGroupType : {}", modelGroupTypeDTO);

    return modelGroupTypeService.partialUpdate(modelGroupTypeDTO);
  }

  /**
   * Get all the modelGroupTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ModelGroupTypeDTO> findAll() {
    log.debug("Request to get all ModelGroupTypes");
    return modelGroupTypeService.findAllStream();
  }

  /**
   * Get one modelGroupType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelGroupTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelGroupType : {}", id);
    return modelGroupTypeService.findOne(id);
  }

  /**
   * Delete the modelGroupType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelGroupType : {}", id);
    return modelGroupTypeService.delete(id);
  }

  /**
   * Returns whether or not a ModelGroupType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ModelGroupType
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ModelGroupType : {}", id);
    return this.modelGroupTypeService.existsById(id);
  }
}
