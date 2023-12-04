package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.service.IntegerParameterService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
@Transactional
public class IntegerParameterFacadeImpl implements IntegerParameterFacade {

  private final Logger log = LoggerFactory.getLogger(IntegerParameterFacadeImpl.class);

  @Autowired private IntegerParameterService integerParameterService;

  /**
   * Save a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to save IntegerParameter : {}", integerParameterDTO);
    return integerParameterService.save(integerParameterDTO);
  }

  /**
   * Update a integerParameter.
   *
   * @param integerParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to update IntegerParameter : {}", integerParameterDTO);
    return integerParameterService.save(integerParameterDTO);
  }

  /**
   * Partially update a integerParameter.
   *
   * @param integerParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO) {
    log.debug("Request to partially update IntegerParameter : {}", integerParameterDTO);

    return integerParameterService.partialUpdate(integerParameterDTO);
  }

  /**
   * Get all the integerParameters.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<IntegerParameterDTO> findAll() {
    log.debug("Request to get all IntegerParameters");
    return integerParameterService.findAllStream();
  }

  /**
   * Get one integerParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<IntegerParameterDTO> findOne(UUID id) {
    log.debug("Request to get IntegerParameter : {}", id);
    return integerParameterService.findOne(id);
  }

  /**
   * Delete the integerParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete IntegerParameter : {}", id);
    return integerParameterService.delete(id);
  }

  /**
   * Returns wether or not a IntegerParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the IntegerParameter
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete IntegerParameter : {}", id);
    return this.integerParameterService.existsById(id);
  }
}
