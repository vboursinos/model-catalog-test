package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.repository.ModelTypeRepository;
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
public class ModelTypeServiceTest {

  private static final String EXISTING_MODEL_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_TYPE_ID = "123e4567-e89b-12d3-a456-426614174099";
  private static final String EXISTING_MODEL_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Mock private ModelTypeRepository repository;

  @Mock private MapperInterface<ModelTypeDTO, ModelType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ModelTypeServiceImpl modelTypeService;

  private ModelTypeDTO getModelTypeDTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setName("test_name");
    return modelTypeDTO;
  }

  private ModelTypeDTO getUpdatedModelTypeDTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.fromString(EXISTING_MODEL_TYPE_ID_FOR_UPDATE));
    modelTypeDTO.setName("test_updated_modeltype");
    return modelTypeDTO;
  }

  private ModelTypeDTO getSavedModelTypeDTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.fromString(EXISTING_MODEL_TYPE_ID));
    modelTypeDTO.setName("test_name");
    return modelTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelTypeService, "context", context);
    ReflectionTestUtils.setField(modelTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(modelTypeService, "repository", repository);
    ReflectionTestUtils.setField(modelTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllModelTypeService() {
    List<ModelTypeDTO> modelTypeDTOList = List.of(getModelTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<List<ModelTypeDTO>, ModelTypeDTO, ModelType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelTypeDTOList);

    Mono<List<ModelTypeDTO>> modelTypes = modelTypeService.findAll();
    StepVerifier.create(modelTypes).expectNext(modelTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdModelTypeService() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelTypeDTO, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelTypeDTO());

    Mono<ModelTypeDTO> modelType = modelTypeService.findOne(existingId);

    StepVerifier.create(modelType)
        .expectNextMatches(
            modelTypeDTO -> {
              Assert.assertEquals(getModelTypeDTO().getName(), modelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelTypeDTO, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelTypeDTO> modelType = modelTypeService.findOne(nonExistingId);

    StepVerifier.create(modelType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testUpdateModelTypeService() {
    ModelTypeDTO updatedModelTypeDTO = getUpdatedModelTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelTypeDTO, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(updatedModelTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedModelTypeDTO);

    Mono<ModelTypeDTO> updatedModelType = modelTypeService.update(updatedModelTypeDTO);

    StepVerifier.create(updatedModelType)
        .expectNextMatches(
            modelTypeDTO -> {
              Assert.assertEquals(updatedModelTypeDTO.getName(), modelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelTypeService() {
    ModelTypeDTO updatedModelTypeDTO = getUpdatedModelTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelTypeDTO, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(updatedModelTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(updatedModelTypeDTO);

    Mono<ModelTypeDTO> updatedModelType = modelTypeService.partialUpdate(updatedModelTypeDTO);

    StepVerifier.create(updatedModelType)
        .expectNextMatches(
            modelTypeDTO -> {
              Assert.assertEquals(updatedModelTypeDTO.getName(), modelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdModelTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);

    Mono<Boolean> exists = modelTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);

    Mono<Boolean> exists = modelTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelTypeService() {
    ModelTypeDTO modelTypeDTO = getSavedModelTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelTypeDTO, ModelTypeDTO, ModelType> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(modelTypeDTO),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelTypeDTO);

    Mono<ModelTypeDTO> savedModelType = modelTypeService.save(modelTypeDTO);

    StepVerifier.create(savedModelType)
        .expectNextMatches(
            savedModelTypeDTO -> {
              Assert.assertEquals(modelTypeDTO.getName(), savedModelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
