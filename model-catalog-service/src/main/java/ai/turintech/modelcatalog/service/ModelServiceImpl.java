package ai.turintech.modelcatalog.service;

import ai.turintech.components.jpa.search.service.AbstractSearchService;
import ai.turintech.components.jpa.search.service.SearchService;
import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.callable.GenericModelCallableImpl;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelLimited;
import ai.turintech.modelcatalog.repository.ModelRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/** Service Implementation for managing {@link Model}. */
@Service
@Transactional
public class ModelServiceImpl extends AbstractSearchService<ModelLimited, ModelDTO>
    implements SearchService<ModelDTO>, ModelService {
  private Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ModelRepository modelRepository;
  @Autowired private ModelMapper modelMapper;

  public ModelServiceImpl() {
    super(ModelLimited.class, ModelDTO.class);
  }

  /**
   * Save a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> save(ModelDTO modelDTO) {
    log.debug("Request to save Model : {}", modelDTO);
    GenericModelCallable<ModelDTO, ModelDTO, Model> callable =
        context.getBean(
            GenericModelCallableImpl.class, "create", modelDTO, modelRepository, modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a model.
   *
   * @param modelDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> update(ModelDTO modelDTO) {
    log.debug("Request to update Model : {}", modelDTO);
    GenericModelCallable<ModelDTO, ModelDTO, Model> callable =
        context.getBean(
            GenericModelCallableImpl.class, "update", modelDTO, modelRepository, modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a model.
   *
   * @param modelDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelDTO> partialUpdate(ModelDTO modelDTO) {
    log.debug("Request to partially update Model : {}", modelDTO);
    GenericModelCallable<ModelDTO, ModelDTO, Model> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "partialUpdate",
            modelDTO.getId(),
            modelDTO,
            modelRepository,
            modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the models with eager load of many-to-many relationships.
   *
   * @return the list of entities.
   */
  public Page<ModelDTO> findAllWithEagerRelationships(Pageable pageable) {
    List<Model> models = modelRepository.findAllWithEagerRelationships();
    return modelRepository.findAllWithEagerRelationships(pageable).map(modelMapper::toDto);
  }

  /**
   * Get one model by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelDTO> findOne(UUID id) throws Exception {
    log.debug("Request to get Model : {}", id);
    GenericModelCallable<ModelDTO, ModelDTO, Model> callable =
        context.getBean(
            GenericModelCallableImpl.class, "findById", id, modelRepository, modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the model by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete Model : {}", id);
    GenericModelCallable<Void, ModelDTO, Model> callable =
        context.getBean(GenericModelCallableImpl.class, "delete", id, modelRepository, modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, ModelDTO, Model> callable =
        context.getBean(
            GenericModelCallableImpl.class, "existsById", id, modelRepository, modelMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
