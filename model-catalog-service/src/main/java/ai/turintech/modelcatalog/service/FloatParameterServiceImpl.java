package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterMapper;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.repository.FloatParameterRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
@Transactional
public class FloatParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<FloatParameterDTO, FloatParameter, UUID>
    implements FloatParameterService {

  @Autowired private FloatParameterRepository repository;
  @Autowired private FloatParameterMapper mapperInterface;

  @Override
  @Transactional
  public Flux<FloatParameterDTO> findAllStream() {
    return Flux.fromIterable(repository.findAll().stream().map(mapperInterface::to).toList())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
