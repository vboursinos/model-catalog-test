package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
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
public class IntegerParameterServiceTest {

  private final String EXISTING_INTEGER_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String NON_EXISTING_INTEGER_PARAMETER_ID = UUID.randomUUID().toString();

  @Mock private ApplicationContext context;

  @Mock private IntegerParameterRepository repository;

  @Mock private MapperInterface<IntegerParameterDTO, IntegerParameter> mapperInterface;

  @InjectMocks private IntegerParameterServiceImpl integerParameterServiceImpl;

  private IntegerParameterDTO getIntegerParameterDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    IntegerParameterDTO integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setId(UUID.fromString(EXISTING_INTEGER_PARAMETER_ID));
    integerParameterDTO.setDefaultValue(1);
    return integerParameterDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(integerParameterServiceImpl, "context", context);
    ReflectionTestUtils.setField(
        integerParameterServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(integerParameterServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(integerParameterServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllIntegerParameterService() {

    List<IntegerParameterDTO> integerParameterDTOList = Arrays.asList(getIntegerParameterDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<IntegerParameterDTO>, IntegerParameterDTO, IntegerParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(integerParameterDTOList);

    Mono<List<IntegerParameterDTO>> integerParametersMono = integerParameterServiceImpl.findAll();
    StepVerifier.create(integerParametersMono).expectNext(integerParameterDTOList).verifyComplete();
  }

  @Test
  void testFindByIdIntegerParameterService() {
    UUID existingId = UUID.fromString(EXISTING_INTEGER_PARAMETER_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            IntegerParameterDTO, IntegerParameterDTO, IntegerParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(getIntegerParameterDTO());

    Mono<IntegerParameterDTO> integerParameterDTOMono =
        integerParameterServiceImpl.findOne(existingId);

    StepVerifier.create(integerParameterDTOMono)
        .expectNextMatches(
            integerParameterDTO -> {
              Assert.assertEquals(
                  getIntegerParameterDTO().getDefaultValue(),
                  integerParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdIntegerParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_INTEGER_PARAMETER_ID);
    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, IntegerParameterDTO, IntegerParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = integerParameterServiceImpl.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdIntegerParameterServiceForNonExistingId() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, IntegerParameterDTO, IntegerParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(UUID.fromString(NON_EXISTING_INTEGER_PARAMETER_ID)),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(false);

    Mono<Boolean> exists =
        integerParameterServiceImpl.existsById(UUID.fromString(NON_EXISTING_INTEGER_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
