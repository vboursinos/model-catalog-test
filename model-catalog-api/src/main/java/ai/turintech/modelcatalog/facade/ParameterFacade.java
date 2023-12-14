package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParameterFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ParameterDTO, UUID> {

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
