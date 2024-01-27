package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudCallable;
import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterMapper;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.repository.ParameterRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/** Service Implementation for managing {@link Parameter}. */
@Service
public class ParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ParameterDTO, Parameter>
    implements ParameterService {

  private final Logger log = LoggerFactory.getLogger(ParameterServiceImpl.class);

  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ParameterRepository parameterRepository;

  @Autowired private ParameterMapper parameterMapper;

  /**
   * Get all the parameters in pages as Mono.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ParameterDTO>> findAllPageable(Pageable pageable) {
    log.debug("Request to get all Parameters");
    ReactiveUUIDIdentityCrudCallable<List<ParameterDTO>, ParameterDTO> callable =
        context.getBean(
            ReactiveUUIDIdentityCrudCallable.class,
            "findAll",
            parameterRepository,
            parameterMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameters in pages as a stream.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterDTO> findAllStream(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return Flux.fromIterable(
        parameterRepository.findAll(pageable).map(parameterMapper::to).getContent());
  }
}
