package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterMapper;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
@Transactional
public class IntegerParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<IntegerParameterDTO, IntegerParameter, UUID>
    implements IntegerParameterService {

  @Autowired private IntegerParameterRepository repository;
  @Autowired private IntegerParameterMapper mapperInterface;

  @Override
  @Transactional
  public Flux<IntegerParameterDTO> findAllStream() {
    System.out.println("CategoricalParameterServiceImpl.findAllStream");
    return Flux.fromIterable(repository.findAll().stream().map(mapperInterface::to).toList())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
