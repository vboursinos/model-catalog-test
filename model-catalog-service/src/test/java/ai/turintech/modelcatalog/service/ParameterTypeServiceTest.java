package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.repository.ParameterTypeRepository;
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
public class ParameterTypeServiceTest {

  private static final String EXISTING_PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_PARAMETER_TYPE_ID =
      "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_PARAMETER_TYPE_UPDATED_ID =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Mock private ParameterTypeRepository repository;

  @Mock private MapperInterface<ParameterTypeDTO, ParameterType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ParameterTypeServiceImpl parameterTypeService;

  private ParameterTypeDTO getParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("test_name");
    return parameterTypeDTO;
  }

  private ParameterTypeDTO getUpdatedParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString(EXISTING_PARAMETER_TYPE_UPDATED_ID));
    parameterTypeDTO.setName("test_updated_parametertype");
    return parameterTypeDTO;
  }

  private ParameterTypeDTO getSavedParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString(EXISTING_PARAMETER_TYPE_ID));
    parameterTypeDTO.setName("test_name");
    return parameterTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(parameterTypeService, "context", context);
    ReflectionTestUtils.setField(parameterTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(parameterTypeService, "repository", repository);
    ReflectionTestUtils.setField(parameterTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllParameterTypeService() {
    List<ParameterTypeDTO> parameterTypeDTOList = List.of(getParameterTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ParameterTypeDTO>, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(parameterTypeDTOList);

    Mono<List<ParameterTypeDTO>> parameterTypes = parameterTypeService.findAll();
    StepVerifier.create(parameterTypes).expectNext(parameterTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdParameterTypeService() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getParameterTypeDTO());

    Mono<ParameterTypeDTO> parameterType = parameterTypeService.findOne(existingId);

    StepVerifier.create(parameterType)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(getParameterTypeDTO().getName(), parameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdParameterTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);

    Mono<Boolean> exists = parameterTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdParameterTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_PARAMETER_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);

    Mono<Boolean> exists = parameterTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveParameterTypeService() {
    ParameterTypeDTO parameterTypeDTO = getSavedParameterTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(parameterTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(parameterTypeDTO);

    Mono<ParameterTypeDTO> savedParameterType = parameterTypeService.save(parameterTypeDTO);

    StepVerifier.create(savedParameterType)
        .expectNextMatches(
            savedParameterTypeDTO -> {
              Assert.assertEquals(parameterTypeDTO.getName(), savedParameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateParameterTypeService() {
    ParameterTypeDTO updatedParameterTypeDTO = getUpdatedParameterTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(updatedParameterTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedParameterTypeDTO);

    Mono<ParameterTypeDTO> updatedParameterType =
        parameterTypeService.update(updatedParameterTypeDTO);

    StepVerifier.create(updatedParameterType)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(updatedParameterTypeDTO.getName(), parameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateParameterTypeService() {
    ParameterTypeDTO updatedParameterTypeDTO = getUpdatedParameterTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(updatedParameterTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedParameterTypeDTO);

    Mono<ParameterTypeDTO> partialUpdatedParameterType =
        parameterTypeService.partialUpdate(updatedParameterTypeDTO);

    StepVerifier.create(partialUpdatedParameterType)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(updatedParameterTypeDTO.getName(), parameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
