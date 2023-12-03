package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.service.CategoricalParameterService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link CategoricalParameter} */
@Service
@Transactional
public class CategoricalParameterFacadeImpl implements CategoricalParameterFacade {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterFacadeImpl.class);

  @Autowired
  private CategoricalParameterService categoricalParameterService;

  /**
   * Save a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> save(CategoricalParameterDTO categoricalParameterDTO) {
    log.debug("Request to save CategoricalParameter : {}", categoricalParameterDTO);
    return categoricalParameterService.save(categoricalParameterDTO);
  }

  /**
   * Update a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to save.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> update(CategoricalParameterDTO categoricalParameterDTO) {
    log.debug("Request to update CategoricalParameter : {}", categoricalParameterDTO);
    return categoricalParameterService.save(categoricalParameterDTO);
  }

  /**
   * Partially update a categoricalParameter.
   *
   * @param categoricalParameterDTO the entity to update partially.
   * @return the persisted entity.
   */
  public Mono<CategoricalParameterDTO> partialUpdate(
      CategoricalParameterDTO categoricalParameterDTO) {
    log.debug("Request to partially update CategoricalParameter : {}", categoricalParameterDTO);
    return categoricalParameterService.partialUpdate(categoricalParameterDTO);
  }

  /**
   * Get all the categoricalParameters.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<CategoricalParameterDTO> findAll() {
    log.debug("Request to get all CategoricalParameters");
    return categoricalParameterService.findAllStream();
  }

  /**
   * Get one categoricalParameter by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<CategoricalParameterDTO> findOne(UUID id) {
    log.debug("Request to get CategoricalParameter : {}", id);
    return categoricalParameterService.findOne(id);
  }

  /**
   * Delete the categoricalParameter by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete CategoricalParameter : {}", id);
    return categoricalParameterService.delete(id);
  }

  /**
   * Returns wether or not a BooleanParameter exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the CategoricalParameter
   */
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to determine existence of CategoricalParameter : {}", id);
    return this.categoricalParameterService.existsById(id);
  }
}
