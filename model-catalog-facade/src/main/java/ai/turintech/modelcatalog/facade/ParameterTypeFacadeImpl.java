package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.service.ParameterTypeService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ParameterType}. */
@Service
@Transactional
public class ParameterTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ParameterTypeDTO, ParameterType, UUID>
    implements ParameterTypeFacade {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeFacadeImpl.class);

  @Autowired private ParameterTypeService parameterTypeService;

  /**
   * Save a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterTypeDTO> save(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to save ParameterType : {}", parameterTypeDTO);
    return parameterTypeService.save(parameterTypeDTO);
  }

  /**
   * Update a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterTypeDTO> update(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to update ParameterType : {}", parameterTypeDTO);
    return parameterTypeService.update(parameterTypeDTO);
  }

  /**
   * Partially update a parameterType.
   *
   * @param parameterTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterTypeDTO> partialUpdate(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to partially update ParameterType : {}", parameterTypeDTO);
    return parameterTypeService.partialUpdate(parameterTypeDTO);
  }

  /**
   * Get all the parameterTypes.
   *
   * @return the list of entities.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDTO> findAll() {
    log.debug("Request to get all ParameterTypes");
    return parameterTypeService.findAllStream();
  }

  /**
   * Get one parameterType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<ParameterTypeDTO> findOne(UUID id) {
    log.debug("Request to get ParameterType : {}", id);
    return parameterTypeService.findOne(id);
  }

  /**
   * Delete the parameterType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Override
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ParameterType : {}", id);
    return parameterTypeService.delete(id);
  }

  /**
   * Returns whether or not a ParameterType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterType
   */
  @Override
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ParameterType : {}", id);
    return this.parameterTypeService.existsById(id);
  }
}
