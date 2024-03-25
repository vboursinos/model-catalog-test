package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.repository.ParameterDistributionTypeRepository;
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
public class ParameterDistributionTypeServiceTest {

  private static final String EXISTING_PARAMETER_DISTRIBUTION_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_PARAMETER_DISTRIBUTION_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Mock private ParameterDistributionTypeRepository repository;

  @Mock
  private MapperInterface<ParameterDistributionTypeDTO, ParameterDistributionType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ParameterDistributionTypeServiceImpl parameterDistributionTypeService;

  private ParameterDistributionTypeDTO getParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("test_name");
    return parameterDistributionTypeDTO;
  }

  private ParameterDistributionTypeDTO getUpdatedParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    parameterDistributionTypeDTO.setName("test_updated_parametertype");
    return parameterDistributionTypeDTO;
  }

  private ParameterDistributionTypeDTO getSavedParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    parameterDistributionTypeDTO.setName("test_name");
    return parameterDistributionTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(parameterDistributionTypeService, "context", context);
    ReflectionTestUtils.setField(
        parameterDistributionTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(parameterDistributionTypeService, "repository", repository);
    ReflectionTestUtils.setField(
        parameterDistributionTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllParameterDistributionTypeService() {
    List<ParameterDistributionTypeDTO> parameterDistributionTypeDTOList =
        List.of(getParameterDistributionTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ParameterDistributionTypeDTO>,
            ParameterDistributionTypeDTO,
            ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(parameterDistributionTypeDTOList);

    Mono<List<ParameterDistributionTypeDTO>> parameterDistributionTypes =
        parameterDistributionTypeService.findAll();
    StepVerifier.create(parameterDistributionTypes)
        .expectNext(parameterDistributionTypeDTOList)
        .verifyComplete();
  }

  @Test
  void testFindByIdParameterDistributionTypeService() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getParameterDistributionTypeDTO());

    Mono<ParameterDistributionTypeDTO> parameterDistributionType =
        parameterDistributionTypeService.findOne(existingId);

    StepVerifier.create(parameterDistributionType)
        .expectNextMatches(
            parameterDistributionTypeDTO -> {
              Assert.assertEquals(
                  getParameterDistributionTypeDTO().getName(),
                  parameterDistributionTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdParameterDistributionTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            Boolean, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = parameterDistributionTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdParameterDistributionTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_PARAMETER_DISTRIBUTION_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            Boolean, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(false);

    Mono<Boolean> exists = parameterDistributionTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveParameterDistributionTypeService() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO =
        getSavedParameterDistributionTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(parameterDistributionTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(parameterDistributionTypeDTO);

    Mono<ParameterDistributionTypeDTO> savedParameterDistributionType =
        parameterDistributionTypeService.save(parameterDistributionTypeDTO);

    StepVerifier.create(savedParameterDistributionType)
        .expectNextMatches(
            savedDTO -> {
              Assert.assertEquals(parameterDistributionTypeDTO.getName(), savedDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateParameterDistributionTypeService() {
    ParameterDistributionTypeDTO updatedParameterDistributionTypeDTO =
        getUpdatedParameterDistributionTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(updatedParameterDistributionTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedParameterDistributionTypeDTO);

    Mono<ParameterDistributionTypeDTO> updatedParameterDistributionType =
        parameterDistributionTypeService.update(updatedParameterDistributionTypeDTO);

    StepVerifier.create(updatedParameterDistributionType)
        .expectNextMatches(
            updatedDTO -> {
              Assert.assertEquals(
                  updatedParameterDistributionTypeDTO.getName(), updatedDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateParameterDistributionTypeService() {
    ParameterDistributionTypeDTO updatedParameterDistributionTypeDTO =
        getUpdatedParameterDistributionTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(updatedParameterDistributionTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedParameterDistributionTypeDTO);

    Mono<ParameterDistributionTypeDTO> partialUpdatedParameterDistributionType =
        parameterDistributionTypeService.partialUpdate(updatedParameterDistributionTypeDTO);

    StepVerifier.create(partialUpdatedParameterDistributionType)
        .expectNextMatches(
            updatedDTO -> {
              Assert.assertEquals(
                  updatedParameterDistributionTypeDTO.getName(), updatedDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
