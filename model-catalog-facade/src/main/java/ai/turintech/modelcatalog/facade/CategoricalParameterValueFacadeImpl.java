package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.service.CategoricalParameterValueService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Service
@Transactional
public class CategoricalParameterValueFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<
        CategoricalParameterValueDTO, CategoricalParameterValue, UUID>
    implements CategoricalParameterValueFacade {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterValueFacadeImpl.class);

  @Autowired private CategoricalParameterValueService categoricalParameterValueService;

  /**
   * Save a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<CategoricalParameterValueDTO> save(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug("Request to save CategoricalParameterValue : {}", categoricalParameterValueDTO);
    return categoricalParameterValueService.save(categoricalParameterValueDTO);
  }

  /**
   * Update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to save.
   * @return the persisted entity.
   */
  @Override
  public Mono<CategoricalParameterValueDTO> update(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug("Request to update CategoricalParameterValue : {}", categoricalParameterValueDTO);
    return categoricalParameterValueService.save(categoricalParameterValueDTO);
  }

  /**
   * Partially update a categoricalParameterValue.
   *
   * @param categoricalParameterValueDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Override
  public Mono<CategoricalParameterValueDTO> partialUpdate(
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    log.debug(
        "Request to partially update CategoricalParameterValue : {}", categoricalParameterValueDTO);

    return categoricalParameterValueService.partialUpdate(categoricalParameterValueDTO);
  }

  /**
   * Get all the categoricalParameterValues.
   *
   * @return the list of entities.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<CategoricalParameterValueDTO> findAll() {
    log.debug("Request to get all CategoricalParameterValues");
    return categoricalParameterValueService.findAllStream();
  }

  /**
   * Get one categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<CategoricalParameterValueDTO> findOne(UUID id) {
    log.debug("Request to get CategoricalParameterValue : {}", id);
    return categoricalParameterValueService.findOne(id);
  }

  /**
   * Delete the categoricalParameterValue by id.
   *
   * @param id the id of the entity.
   * @return a Mono to signal the deletion
   */
  @Override
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete CategoricalParameterValue : {}", id);
    return categoricalParameterValueService.delete(id);
  }

  /**
   * Returns wether or not a CategoricalParameterValue exists with provided id.
   *
   * @param id
   * @return a Mono to signal the existence of the categoricalParameterValue
   */
  @Override
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to delete CategoricalParameterValue : {}", id);
    return this.categoricalParameterValueService.existsById(id);
  }
}
