package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterMapper;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.repository.CategoricalParameterRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/** Service Implementation for managing {@link CategoricalParameter}. */
@Service
@Transactional
public class CategoricalParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        CategoricalParameterDTO, CategoricalParameter, UUID>
    implements CategoricalParameterService {
  @Autowired private CategoricalParameterRepository repository;
  @Autowired private CategoricalParameterMapper mapperInterface;

  @Override
  @Transactional
  public Flux<CategoricalParameterDTO> findAllStream() {
    return Flux.fromIterable(repository.findAll().stream().map(mapperInterface::to).toList())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
