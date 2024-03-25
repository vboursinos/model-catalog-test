package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import ai.turintech.modelcatalog.repository.MetricRepository;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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

@ExtendWith({MockitoExtension.class})
public class MlTaskTypeServiceTest {

  // String IDs for testing
  private static final String EXISTING_ML_TASK_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_ML_TASK_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String NON_EXISTING_ML_TASK_TYPE_ID = "123e4567-e89b-12d3-a456-426614174099";

  private static final String ML_TASK_ID_SAVE_RESPONSE = "123e4567-e89b-12d3-a456-426614174099";
  @Mock private ApplicationContext context;
  @Mock private MetricRepository repository;
  @Mock private MapperInterface<MlTaskTypeDTO, MlTaskType> mapperInterface;
  @InjectMocks private MlTaskTypeServiceImpl mlTaskTypeServiceImpl;

  private MlTaskTypeDTO getMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setName("test_name");
    return mlTaskTypeDTO;
  }

  private MlTaskTypeDTO getUpdatedMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.fromString(EXISTING_ML_TASK_TYPE_ID_FOR_UPDATE));
    mlTaskTypeDTO.setName("test_updated_mltasktype");
    return mlTaskTypeDTO;
  }

  private MlTaskTypeDTO getMlTaskTypeSavedResponseDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.fromString(ML_TASK_ID_SAVE_RESPONSE));
    mlTaskTypeDTO.setName("test_save_mltasktype");
    return mlTaskTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(mlTaskTypeServiceImpl, "context", context);
    ReflectionTestUtils.setField(mlTaskTypeServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(mlTaskTypeServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(mlTaskTypeServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllMlTaskTypeService() {

    List<MlTaskTypeDTO> mlTaskTypeDTOList = Arrays.asList(getMlTaskTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<List<MlTaskTypeDTO>, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(mlTaskTypeDTOList);

    Mono<List<MlTaskTypeDTO>> mlTaskTypes = mlTaskTypeServiceImpl.findAll();
    StepVerifier.create(mlTaskTypes).expectNext(mlTaskTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdMlTaskTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_ML_TASK_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getMlTaskTypeDTO());

    Mono<MlTaskTypeDTO> mlTaskType = mlTaskTypeServiceImpl.findOne(existingId);

    StepVerifier.create(mlTaskType)
        .expectNextMatches(
            mlTaskTypeDTO -> {
              Assert.assertEquals("test_name", mlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdMlTaskTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_ML_TASK_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<MlTaskTypeDTO> mlTaskType = mlTaskTypeServiceImpl.findOne(nonExistingId);

    StepVerifier.create(mlTaskType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdMlTaskTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_ML_TASK_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, MlTaskTypeDTO, MlTaskType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(true);

    Mono<Boolean> existsForExistingId = mlTaskTypeServiceImpl.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdMlTaskTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_ML_TASK_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, MlTaskTypeDTO, MlTaskType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(false);

    Mono<Boolean> existsForNonExistingId = mlTaskTypeServiceImpl.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveMlTaskTypeService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getMlTaskTypeSavedResponseDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getMlTaskTypeSavedResponseDTO());

    Mono<MlTaskTypeDTO> savedMlTaskType =
        mlTaskTypeServiceImpl.save(getMlTaskTypeSavedResponseDTO());

    StepVerifier.create(savedMlTaskType)
        .expectNextMatches(
            savedMlTaskTypeDTO -> {
              Assert.assertEquals(
                  getMlTaskTypeSavedResponseDTO().getName(), savedMlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateMlTaskTypeService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedMlTaskTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedMlTaskTypeDTO());

    Mono<MlTaskTypeDTO> updatedMlTaskType = mlTaskTypeServiceImpl.update(getUpdatedMlTaskTypeDTO());

    StepVerifier.create(updatedMlTaskType)
        .expectNextMatches(
            updatedMlTaskTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedMlTaskTypeDTO().getName(), updatedMlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateMlTaskTypeService() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedMlTaskTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedMlTaskTypeDTO());

    Mono<MlTaskTypeDTO> updatedMlTaskType =
        mlTaskTypeServiceImpl.partialUpdate(getUpdatedMlTaskTypeDTO());

    StepVerifier.create(updatedMlTaskType)
        .expectNextMatches(
            updatedMlTaskTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedMlTaskTypeDTO().getName(), updatedMlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testDeleteMetricService() {
    UUID existingId = UUID.fromString(EXISTING_ML_TASK_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Void, MlTaskTypeDTO, MlTaskType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("delete"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    mlTaskTypeServiceImpl.delete(existingId);
  }
}
