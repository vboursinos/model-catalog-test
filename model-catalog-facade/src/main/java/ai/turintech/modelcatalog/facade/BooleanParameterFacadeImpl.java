package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.service.BooleanParameterService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link BooleanParameter}. */
@Service
@Transactional
public class BooleanParameterFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<BooleanParameterDTO, BooleanParameter, UUID>
    implements BooleanParameterFacade {

  private final Logger log = LoggerFactory.getLogger(BooleanParameterFacadeImpl.class);

  @Autowired private BooleanParameterService booleanParameterService;

  /**
   * Save a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<BooleanParameterDTO> save(BooleanParameterDTO booleanParameterDTO) {
    log.debug("Request to save BooleanParameter : {}", booleanParameterDTO);
    return booleanParameterService.save(booleanParameterDTO);
  }

  /**
   * Update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to save.
   * @return the persisted entity.
   */
  @Autowired
  public Mono<BooleanParameterDTO> update(BooleanParameterDTO booleanParameterDTO) {
    log.debug("Request to update BooleanParameter : {}", booleanParameterDTO);
    return booleanParameterService.save(booleanParameterDTO);
  }

  /**
   * Partially update a booleanParameter.
   *
   * @param booleanParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Override
  public Mono<BooleanParameterDTO> partialUpdate(BooleanParameterDTO booleanParameterDTO) {
    log.debug("Request to partially update BooleanParameter : {}", booleanParameterDTO);
    return booleanParameterService.partialUpdate(booleanParameterDTO);
  }

  /**
   * Get all the booleanParameters.
   *
   * @return the list of entities.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<BooleanParameterDTO> findAll() {
    log.debug("Request to get all BooleanParameters");
    return booleanParameterService.findAllStream();
  }

  /**
   * Get one booleanParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<BooleanParameterDTO> findOne(UUID id) {
    log.debug("Request to get BooleanParameter : {}", id);
    return booleanParameterService.findOne(id);
  }

  /**
   * Delete the booleanParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Override
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete BooleanParameter : {}", id);
    return booleanParameterService.delete(id);
  }

  /**
   * Returns wether or not a BooleanParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the BooleanParameter
   */
  @Override
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete BooleanParameter : {}", id);
    return this.booleanParameterService.existsById(id);
  }
}
