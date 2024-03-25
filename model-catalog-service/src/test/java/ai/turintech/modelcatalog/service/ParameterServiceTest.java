package ai.turintech.modelcatalog.service;

import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudCallable;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterMapper;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.repository.ParameterRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ParameterServiceTest {

  private static final String EXISTING_ID_STRING = "523e4567-e89b-12d3-a456-426614174001";
  @Mock private ParameterRepository repository;

  @Mock private MapperInterface<ParameterDTO, Parameter> mapperInterface;

  @Mock private ParameterMapper parameterMapper;

  @Mock private ApplicationContext context;
  @InjectMocks private ParameterServiceImpl parameterService;

  private ParameterDTO getParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setName("test_parameter");
    parameterDTO.setDescription("test_description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setLabel("test_label");
    parameterDTO.setOrdering(1);
    return parameterDTO;
  }

  private ParameterDTO getSavedParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setId(UUID.fromString(EXISTING_ID_STRING));
    parameterDTO.setName("update_parameter");
    parameterDTO.setDescription("test_description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setLabel("test_label");
    parameterDTO.setOrdering(1);
    return parameterDTO;
  }

  private Parameter getSavedParameter() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.fromString(EXISTING_ID_STRING));
    parameter.setName("update_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);
    return parameter;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(parameterService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(parameterService, "context", context);
    ReflectionTestUtils.setField(parameterService, "repository", repository);
    ReflectionTestUtils.setField(parameterService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllParameterService() {
    List<ParameterDTO> parameterDTOList = List.of(getParameterDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<List<ParameterDTO>, ParameterDTO, Parameter>
        callable = Mockito.mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    Mockito.when(
            context.getBean(
                Mockito.eq(ReactiveUUIDIdentityCrudCallable.class),
                Mockito.eq("findAll"),
                Mockito.eq(repository),
                Mockito.eq(parameterMapper)))
        .thenReturn(callable);

    Mockito.when(callable.call()).thenReturn(parameterDTOList);
    Pageable pageable = Pageable.unpaged();
    Mono<List<ParameterDTO>> parametersMono = parameterService.findAllPageable(pageable);
    StepVerifier.create(parametersMono).expectNext(parameterDTOList).verifyComplete();
  }

  @Test
  void testFindAllStreamParameterService() {
    List<Parameter> parameterList = List.of(getSavedParameter());
    List<ParameterDTO> parameterDTOList = List.of(getSavedParameterDTO());

    Page<Parameter> parameterPage = new PageImpl<>(parameterList);
    when(repository.findAll(Pageable.unpaged())).thenReturn(parameterPage);

    when(parameterMapper.to(parameterList.get(0))).thenReturn(parameterDTOList.get(0));

    Flux<ParameterDTO> parameters = parameterService.findAllStream(Pageable.unpaged());

    StepVerifier.create(parameters).expectNext(parameterDTOList.get(0)).verifyComplete();
  }
}
