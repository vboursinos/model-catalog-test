package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.service.IntegerParameterValueService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Service
@Transactional
public class IntegerParameterValueFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<IntegerParameterValueDTO, IntegerParameterValue, UUID>
    implements IntegerParameterValueFacade {

  private final Logger log = LoggerFactory.getLogger(IntegerParameterValueFacadeImpl.class);

  @Autowired private IntegerParameterValueService integerParameterValueService;

  /**
   * Save a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<IntegerParameterValueDTO> save(IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to save IntegerParameterValue : {}", integerParameterValueDTO);
    return integerParameterValueService.save(integerParameterValueDTO);
  }

  /**
   * Update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<IntegerParameterValueDTO> update(IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to update IntegerParameterValue : {}", integerParameterValueDTO);
    return integerParameterValueService.update(integerParameterValueDTO);
  }

  /**
   * Partially update a integerParameterValue.
   *
   * @param integerParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Override
  public Mono<IntegerParameterValueDTO> partialUpdate(
      IntegerParameterValueDTO integerParameterValueDTO) {
    log.debug("Request to partially update IntegerParameterValue : {}", integerParameterValueDTO);

    return integerParameterValueService.partialUpdate(integerParameterValueDTO);
  }

  /**
   * Get all the integerParameterValues.
   *
   * @return the list of entities.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<IntegerParameterValueDTO> findAll() {
    log.debug("Request to get all IntegerParameterValues");
    return integerParameterValueService.findAllStream();
  }

  /**
   * Get one integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<IntegerParameterValueDTO> findOne(UUID id) {
    log.debug("Request to get IntegerParameterValue : {}", id);
    return integerParameterValueService.findOne(id);
  }

  /**
   * Delete the integerParameterValue by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Override
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete IntegerParameterValue : {}", id);
    return integerParameterValueService.delete(id);
  }

  /**
   * Returns whether or not a IntegerParameterValue exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the IntegerParameterValue
   */
  @Override
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete IntegerParameterValue : {}", id);
    return this.integerParameterValueService.existsById(id);
  }
}
