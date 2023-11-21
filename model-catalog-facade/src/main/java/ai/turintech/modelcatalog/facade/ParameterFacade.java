package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.service.ParameterService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link Parameter}. */
@Service
@Transactional
public class ParameterFacade {

  private final Logger log = LoggerFactory.getLogger(ParameterFacade.class);

  private final ParameterService parameterService;

  public ParameterFacade(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * Save a parameter.
   *
   * @param parameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterDTO> save(ParameterDTO parameterDTO) {
    log.debug("Request to save Parameter : {}", parameterDTO);
    return parameterService.save(parameterDTO);
  }

  /**
   * Update a parameter.
   *
   * @param parameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<ParameterDTO> update(ParameterDTO parameterDTO) {
    log.debug("Request to update Parameter : {}", parameterDTO);
    return parameterService.update(parameterDTO);
  }

  /**
   * Partially update a parameter.
   *
   * @param parameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<Optional<ParameterDTO>> partialUpdate(ParameterDTO parameterDTO) {
    log.debug("Request to partially update Parameter : {}", parameterDTO);

    return parameterService.partialUpdate(parameterDTO);
  }

  /**
   * Get all the parameters as a stream.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterDTO> findAllStream(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return parameterService.findAllStream(pageable);
  }

  /**
   * Get all the parameters.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ParameterDTO>> findAll(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return parameterService.findAll(pageable);
  }

  /**
   * Get one parameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ParameterDTO> findOne(UUID id) {
    log.debug("Request to get Parameter : {}", id);
    return parameterService.findOne(id);
  }

  /**
   * Delete the parameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete Parameter : {}", id);
    return parameterService.delete(id);
  }

  /**
   * Returns whether or not a Parameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the Parameter
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete ModelGroupType : {}", id);
    return this.parameterService.existsById(id);
  }
}
