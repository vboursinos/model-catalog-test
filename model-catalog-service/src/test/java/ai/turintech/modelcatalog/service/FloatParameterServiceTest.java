package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.repository.FloatParameterRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FloatParameterServiceTest {

  private final String EXISTING_FLOAT_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String NON_EXISTING_FLOAT_PARAMETER_ID = UUID.randomUUID().toString();

  @Mock private ApplicationContext context;
  @Mock private FloatParameterRepository repository;
  @Mock private MapperInterface<FloatParameterDTO, FloatParameter> mapperInterface;
  @InjectMocks private FloatParameterServiceImpl floatParameterServiceImpl;

  private FloatParameterDTO getFloatParameterDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.fromString(EXISTING_FLOAT_PARAMETER_ID));
    floatParameterDTO.setDefaultValue(1.1);
    return floatParameterDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(floatParameterServiceImpl, "context", context);
    ReflectionTestUtils.setField(
        floatParameterServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(floatParameterServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(floatParameterServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllFloatParameterService() {

    List<FloatParameterDTO> floatParameterDTOList = Arrays.asList(getFloatParameterDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<FloatParameterDTO>, FloatParameterDTO, FloatParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(floatParameterDTOList);

    Mono<List<FloatParameterDTO>> floatParametersMono = floatParameterServiceImpl.findAll();
    StepVerifier.create(floatParametersMono).expectNext(floatParameterDTOList).verifyComplete();
  }

  @Test
  void testFindByIdFloatParameterService() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            FloatParameterDTO, FloatParameterDTO, FloatParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(getFloatParameterDTO());

    Mono<FloatParameterDTO> floatParameterDTOMono = floatParameterServiceImpl.findOne(existingId);

    StepVerifier.create(floatParameterDTOMono)
        .expectNextMatches(
            floatParameterDTO -> {
              Assert.assertEquals(
                  getFloatParameterDTO().getDefaultValue(), floatParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_ID);
    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, FloatParameterDTO, FloatParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = floatParameterServiceImpl.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterServiceForNonExistingId() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, FloatParameterDTO, FloatParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(UUID.fromString(NON_EXISTING_FLOAT_PARAMETER_ID)),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(false);

    Mono<Boolean> exists =
        floatParameterServiceImpl.existsById(UUID.fromString(NON_EXISTING_FLOAT_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
