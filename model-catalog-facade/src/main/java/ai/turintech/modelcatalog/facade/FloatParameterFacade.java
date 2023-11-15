package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.service.FloatParameterService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
@Transactional
public class FloatParameterFacade {

  private final Logger log = LoggerFactory.getLogger(FloatParameterFacade.class);

  private final FloatParameterService floatParameterService;

  public FloatParameterFacade(FloatParameterService floatParameterService) {
    this.floatParameterService = floatParameterService;
  }

  /**
   * Save a floatParameter.
   *
   * @param floatParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> save(FloatParameterDTO floatParameterDTO) {
    log.debug("Request to save FloatParameter : {}", floatParameterDTO);
    return floatParameterService.save(floatParameterDTO);
  }

  /**
   * Update a floatParameter.
   *
   * @param floatParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> update(FloatParameterDTO floatParameterDTO) {
    log.debug("Request to update FloatParameter : {}", floatParameterDTO);
    return floatParameterService.save(floatParameterDTO);
  }

  /**
   * Partially update a floatParameter.
   *
   * @param floatParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<FloatParameterDTO> partialUpdate(FloatParameterDTO floatParameterDTO) {
    log.debug("Request to partially update FloatParameter : {}", floatParameterDTO);

    return floatParameterService.partialUpdate(floatParameterDTO);
  }

  /**
   * Get all the floatParameters.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<FloatParameterDTO> findAll() {
    log.debug("Request to get all FloatParameters");
    return floatParameterService.findAllStream();
  }

  /**
   * Get one floatParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<FloatParameterDTO> findOne(UUID id) {
    log.debug("Request to get FloatParameter : {}", id);
    return floatParameterService.findOne(id);
  }

  /**
   * Delete the floatParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete FloatParameter : {}", id);
    return floatParameterService.delete(id);
  }

  /**
   * Returns wether or not a FloatParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the FloatParameter
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete FloatParameter : {}", id);
    return this.floatParameterService.existsById(id);
  }
}
