package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.service.ParameterDistributionTypeService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Service
@Transactional
public class ParameterDistributionTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<
        ParameterDistributionTypeDTO, ParameterDistributionType, UUID>
    implements ParameterDistributionTypeFacade {

  private final Logger log = LoggerFactory.getLogger(ParameterDistributionTypeFacadeImpl.class);

  @Autowired private ParameterDistributionTypeService parameterDistributionTypeService;

  /**
   * Save a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterDistributionTypeDTO> save(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
    log.debug("Request to save ParameterDistributionType : {}", parameterDistributionTypeDTO);
    return parameterDistributionTypeService.save(parameterDistributionTypeDTO);
  }

  /**
   * Update a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterDistributionTypeDTO> update(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
    log.debug("Request to update ParameterDistributionType : {}", parameterDistributionTypeDTO);
    return parameterDistributionTypeService.update(parameterDistributionTypeDTO);
  }

  /**
   * Partially update a parameterDistributionType.
   *
   * @param parameterDistributionTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Override
  public Mono<ParameterDistributionTypeDTO> partialUpdate(
      ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
    log.debug(
        "Request to partially update ParameterDistributionType : {}", parameterDistributionTypeDTO);

    return parameterDistributionTypeService.partialUpdate(parameterDistributionTypeDTO);
  }

  /**
   * Get all the parameterDistributionTypes.
   *
   * @return the list of entities.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<ParameterDistributionTypeDTO> findAll() {
    log.debug("Request to get all ParameterDistributionTypes");
    return parameterDistributionTypeService.findAllStream();
  }

  /**
   * Get one parameterDistributionType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<ParameterDistributionTypeDTO> findOne(UUID id) {
    log.debug("Request to get ParameterDistributionType : {}", id);
    return parameterDistributionTypeService.findOne(id);
  }

  /**
   * Delete the parameterDistributionType by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Override
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ParameterDistributionType : {}", id);
    return parameterDistributionTypeService.delete(id);
  }

  /**
   * Returns whether or not a ParameterDistributionType exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterDistributionType
   */
  @Override
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ModelGroupType : {}", id);
    return this.parameterDistributionTypeService.existsById(id);
  }
}
