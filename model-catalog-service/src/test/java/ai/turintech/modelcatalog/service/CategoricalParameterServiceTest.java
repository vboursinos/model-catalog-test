package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.repository.CategoricalParameterRepository;
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
public class CategoricalParameterServiceTest {

  private final String EXISTING_CATEGORICAL_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String EXISTING_CATEGORICAL_PARAMETER_TYPE_DEFINITION_ID =
      "323e4567-e89b-12d3-a456-426614174003";
  private final String NON_EXISTING_CATEGORICAL_PARAMETER_ID = UUID.randomUUID().toString();

  @Mock private ApplicationContext context;
  @Mock private CategoricalParameterRepository repository;
  @Mock private MapperInterface<CategoricalParameterDTO, CategoricalParameter> mapperInterface;
  @InjectMocks private CategoricalParameterServiceImpl categoricalParameterServiceImpl;

  private CategoricalParameterDTO getCategoricalParameterDTO() {
    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID));
    categoricalParameterDTO.setDefaultValue("test_default_value");
    return categoricalParameterDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(categoricalParameterServiceImpl, "context", context);
    ReflectionTestUtils.setField(
        categoricalParameterServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(categoricalParameterServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(
        categoricalParameterServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllCategoricalParameterService() {

    List<CategoricalParameterDTO> categoricalParameterDTOList =
        Arrays.asList(getCategoricalParameterDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<CategoricalParameterDTO>, CategoricalParameterDTO, CategoricalParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(categoricalParameterDTOList);

    Mono<List<CategoricalParameterDTO>> categoricalParametersMono =
        categoricalParameterServiceImpl.findAll();
    StepVerifier.create(categoricalParametersMono)
        .expectNext(categoricalParameterDTOList)
        .verifyComplete();
  }

  @Test
  void testFindByIdCategoricalParameterService() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            CategoricalParameterDTO, CategoricalParameterDTO, CategoricalParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(getCategoricalParameterDTO());

    Mono<CategoricalParameterDTO> categoricalParameterDTOMono =
        categoricalParameterServiceImpl.findOne(existingId);

    StepVerifier.create(categoricalParameterDTOMono)
        .expectNextMatches(
            categoricalParameterDTO -> {
              Assert.assertEquals("test_default_value", categoricalParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdCategoricalParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID);
    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            Boolean, CategoricalParameterDTO, CategoricalParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = categoricalParameterServiceImpl.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdCategoricalParameterServiceForNonExistingId() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            Boolean, CategoricalParameterDTO, CategoricalParameter>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(UUID.fromString(NON_EXISTING_CATEGORICAL_PARAMETER_ID)),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(false);

    Mono<Boolean> exists =
        categoricalParameterServiceImpl.existsById(
            UUID.fromString(NON_EXISTING_CATEGORICAL_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
