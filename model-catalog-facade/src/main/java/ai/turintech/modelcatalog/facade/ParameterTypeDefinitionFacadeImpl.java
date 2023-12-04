package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.service.ParameterTypeDefinitionService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link ParameterTypeDefinition}. */
@Service
@Transactional
public class ParameterTypeDefinitionFacadeImpl implements ParameterTypeDefinitionFacade {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeDefinitionFacadeImpl.class);

  @Autowired private ParameterTypeDefinitionService parameterTypeDefinitionService;

  /**
   * Save a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDefinitionDTO> save(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
    log.debug("Request to save ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);
    return parameterTypeDefinitionService.save(parameterTypeDefinitionDTO);
  }

  /**
   * Update a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDefinitionDTO> update(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
    log.debug("Request to update ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);
    return parameterTypeDefinitionService.update(parameterTypeDefinitionDTO);
  }

  /**
   * Partially update a parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<ParameterTypeDefinitionDTO> partialUpdate(
      ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
    log.debug(
        "Request to partially update ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);

    return parameterTypeDefinitionService.partialUpdate(parameterTypeDefinitionDTO);
  }

  /**
   * Get all the parameterTypeDefinitions.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterTypeDefinitionDTO> findAll() {
    log.debug("Request to get all ParameterTypeDefinitions");
    return parameterTypeDefinitionService.findAllStream();
  }

  /**
   * Get one parameterTypeDefinition by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ParameterTypeDefinitionDTO> findOne(UUID id) {
    log.debug("Request to get ParameterTypeDefinition : {}", id);
    return parameterTypeDefinitionService.findOne(id);
  }

  /**
   * Delete the parameterTypeDefinition by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ParameterTypeDefinition : {}", id);
    return parameterTypeDefinitionService.delete(id);
  }

  /**
   * Returns whether or not a ParameterTypeDefinition exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the ParameterTypeDefinition
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ParameterTypeDefinition : {}", id);
    return this.parameterTypeDefinitionService.existsById(id);
  }
}
