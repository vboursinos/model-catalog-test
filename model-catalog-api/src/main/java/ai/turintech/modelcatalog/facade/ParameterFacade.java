package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterFacade extends ReactiveUUIDIdentityCrudFacade<ParameterDTO> {

  /**
   * Get all the parameters in pages as a stream.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  public Flux<ParameterDTO> findPageableStream(Pageable pageable);

  /**
   * Get all the parameters.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  public Mono<List<ParameterDTO>> findAllPageable(Pageable pageable);
}
