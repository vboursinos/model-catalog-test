package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.service.ParameterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Implementation for managing {@link Parameter}. */
@Component
public class ParameterFacadeImpl extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ParameterDTO>
    implements ParameterFacade {

  private final Logger log = LoggerFactory.getLogger(ParameterFacadeImpl.class);

  @Autowired private ParameterService parameterService;

  /**
   * Get all the parameters.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ParameterDTO>> findAllPageable(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return parameterService.findAllPageable(pageable);
  }

  /**
   * Get all the parameters as a stream.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Flux<ParameterDTO> findPageableStream(Pageable pageable) {
    log.debug("Request to get all Parameters");
    return parameterService.findAllStream(pageable);
  }
}
