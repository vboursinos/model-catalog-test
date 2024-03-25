package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.repository.ModelEnsembleTypeRepository;
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

@ExtendWith(MockitoExtension.class)
public class ModelEnsembleTypeServiceTest {

  // String IDs for testing
  private static final String EXISTING_MODEL_ENSEMBLE_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_MODEL_ENSEMBLE_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Mock private ModelEnsembleTypeRepository repository;

  @Mock private MapperInterface<ModelEnsembleTypeDTO, ModelEnsembleType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ModelEnsembleTypeServiceImpl modelEnsembleTypeService;

  private ModelEnsembleTypeDTO getModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setName("test_name");
    return modelEnsembleTypeDTO;
  }

  private ModelEnsembleTypeDTO getUpdatedModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID_FOR_UPDATE));
    modelEnsembleTypeDTO.setName("test_updated_modelEnsembletype");
    return modelEnsembleTypeDTO;
  }

  private ModelEnsembleTypeDTO getSaveModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID_FOR_UPDATE));
    modelEnsembleTypeDTO.setName("test_name");
    return modelEnsembleTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelEnsembleTypeService, "context", context);
    ReflectionTestUtils.setField(modelEnsembleTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(modelEnsembleTypeService, "repository", repository);
    ReflectionTestUtils.setField(modelEnsembleTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllModelEnsembleTypeService() {
    List<ModelEnsembleTypeDTO> modelEnsembleTypeDTOList = List.of(getModelEnsembleTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ModelEnsembleTypeDTO>, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelEnsembleTypeDTOList);

    Mono<List<ModelEnsembleTypeDTO>> modelEnsembleTypes = modelEnsembleTypeService.findAll();
    StepVerifier.create(modelEnsembleTypes).expectNext(modelEnsembleTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelEnsembleTypeDTO());

    Mono<ModelEnsembleTypeDTO> modelEnsembleType = modelEnsembleTypeService.findOne(existingId);

    StepVerifier.create(modelEnsembleType)
        .expectNextMatches(
            modelEnsembleTypeDTO -> {
              Assert.assertEquals("test_name", modelEnsembleTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelEnsembleTypeDTO> modelEnsembleType = modelEnsembleTypeService.findOne(nonExistingId);

    StepVerifier.create(modelEnsembleType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);
    Mono<Boolean> existsForExistingId = modelEnsembleTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);
    Mono<Boolean> existsForNonExistingId = modelEnsembleTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelEnsembleTypeService() {
    ModelEnsembleTypeDTO savedModelEnsembleTypeDTO = getSaveModelEnsembleTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getSaveModelEnsembleTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getSaveModelEnsembleTypeDTO());
    Mono<ModelEnsembleTypeDTO> savedModelEnsembleType =
        modelEnsembleTypeService.save(savedModelEnsembleTypeDTO);

    StepVerifier.create(savedModelEnsembleType)
        .expectNextMatches(
            savedModelEnsembleTypeDTO1 -> {
              Assert.assertEquals(
                  getModelEnsembleTypeDTO().getName(), savedModelEnsembleTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateModelEnsembleTypeService() {
    ModelEnsembleTypeDTO updatedModelEnsembleTypeDTO = getUpdatedModelEnsembleTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedModelEnsembleTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelEnsembleTypeDTO());
    Mono<ModelEnsembleTypeDTO> updatedModelEnsembleType =
        modelEnsembleTypeService.update(updatedModelEnsembleTypeDTO);

    StepVerifier.create(updatedModelEnsembleType)
        .expectNextMatches(
            updatedModelEnsembleTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelEnsembleTypeDTO().getName(),
                  updatedModelEnsembleTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelEnsembleTypeService() {
    ModelEnsembleTypeDTO updatedModelEnsembleTypeDTO = getUpdatedModelEnsembleTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedModelEnsembleTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelEnsembleTypeDTO());

    Mono<ModelEnsembleTypeDTO> updatedModelEnsembleType =
        modelEnsembleTypeService.partialUpdate(updatedModelEnsembleTypeDTO);

    StepVerifier.create(updatedModelEnsembleType)
        .expectNextMatches(
            updatedModelEnsembleTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelEnsembleTypeDTO().getName(),
                  updatedModelEnsembleTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }
}
