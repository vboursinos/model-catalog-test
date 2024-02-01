package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.repository.BooleanParameterRepository;
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
public class BooleanParameterServiceTest {
  private final String EXISTING_BOOLEAN_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String EXISTING_BOOLEAN_PARAMETER_ID_FOR_UPDATE =
      "323e4567-e89b-12d3-a456-426614174003";
  private final String NON_EXISTING_BOOLEAN_PARAMETER_ID = UUID.randomUUID().toString();

  @Mock private ApplicationContext context;
  @Mock private BooleanParameterRepository repository;
  @Mock private MapperInterface<BooleanParameterDTO, BooleanParameter> mapperInterface;
  @InjectMocks private BooleanParameterServiceImpl booleanParameterServiceImpl;

  private BooleanParameterDTO getBooleanParameterDTO() {
    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID_FOR_UPDATE));
    booleanParameterDTO.setDefaultValue(false);
    return booleanParameterDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(booleanParameterServiceImpl, "context", context);
    ReflectionTestUtils.setField(
        booleanParameterServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(booleanParameterServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(booleanParameterServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllBooleanParameterService() {
    List<BooleanParameterDTO> booleanParameterDTOList = Arrays.asList(getBooleanParameterDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<BooleanParameterDTO>, BooleanParameterDTO, BooleanParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(booleanParameterDTOList);

    Mono<List<BooleanParameterDTO>> booleanParametersMono = booleanParameterServiceImpl.findAll();
    StepVerifier.create(booleanParametersMono).expectNext(booleanParameterDTOList).verifyComplete();
  }

  @Test
  void testFindByIdBooleanParameterService() {
    UUID existingId = UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            BooleanParameterDTO, BooleanParameterDTO, BooleanParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID)),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(getBooleanParameterDTO());

    Mono<BooleanParameterDTO> booleanParameterDTOMono =
        booleanParameterServiceImpl.findOne(existingId);

    StepVerifier.create(booleanParameterDTOMono)
        .expectNextMatches(
            booleanParameterDTO -> {
              Assert.assertEquals(false, booleanParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdBooleanParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, BooleanParameterDTO, BooleanParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = booleanParameterServiceImpl.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdBooleanParameterServiceForNonExistingId() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, BooleanParameterDTO, BooleanParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(UUID.fromString(NON_EXISTING_BOOLEAN_PARAMETER_ID)),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(false);

    Mono<Boolean> exists =
        booleanParameterServiceImpl.existsById(UUID.fromString(NON_EXISTING_BOOLEAN_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
