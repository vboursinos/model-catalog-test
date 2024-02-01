package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.repository.ModelFamilyTypeRepository;
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
public class ModelFamilyTypeServiceTest {

  private static final String EXISTING_MODEL_FAMILY_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_MODEL_FAMILY_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String NON_EXISTING_MODEL_FAMILY_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Mock private ModelFamilyTypeRepository repository;

  @Mock private MapperInterface<ModelFamilyTypeDTO, ModelFamilyType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ModelFamilyTypeServiceImpl modelFamilyTypeService;

  private ModelFamilyTypeDTO getModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setName("test_name");
    return modelFamilyTypeDTO;
  }

  private ModelFamilyTypeDTO getUpdatedModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID_FOR_UPDATE));
    modelFamilyTypeDTO.setName("test_updated_modelfamilytype");
    return modelFamilyTypeDTO;
  }

  private ModelFamilyTypeDTO getSavedModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID));
    modelFamilyTypeDTO.setName("test_name");
    return modelFamilyTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelFamilyTypeService, "context", context);
    ReflectionTestUtils.setField(modelFamilyTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(modelFamilyTypeService, "repository", repository);
    ReflectionTestUtils.setField(modelFamilyTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllModelFamilyTypeService() {
    List<ModelFamilyTypeDTO> modelFamilyTypeDTOList = List.of(getModelFamilyTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ModelFamilyTypeDTO>, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelFamilyTypeDTOList);

    Mono<List<ModelFamilyTypeDTO>> modelFamilyTypes = modelFamilyTypeService.findAll();
    StepVerifier.create(modelFamilyTypes).expectNext(modelFamilyTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelFamilyTypeDTO());

    Mono<ModelFamilyTypeDTO> modelFamilyType = modelFamilyTypeService.findOne(existingId);

    StepVerifier.create(modelFamilyType)
        .expectNextMatches(
            modelFamilyTypeDTO -> {
              Assert.assertEquals("test_name", modelFamilyTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_FAMILY_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelFamilyTypeDTO> modelFamilyType = modelFamilyTypeService.findOne(nonExistingId);

    StepVerifier.create(modelFamilyType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);
    Mono<Boolean> existsForExistingId = modelFamilyTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_FAMILY_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);
    Mono<Boolean> existsForNonExistingId = modelFamilyTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteModelFamilyTypeService() {
    ModelFamilyTypeDTO savedModelFamilyTypeDTO = getSavedModelFamilyTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getSavedModelFamilyTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getSavedModelFamilyTypeDTO());
    Mono<ModelFamilyTypeDTO> savedModelFamilyType =
        modelFamilyTypeService.save(savedModelFamilyTypeDTO);

    StepVerifier.create(savedModelFamilyType)
        .expectNextMatches(
            savedModelFamilyTypeDTO1 -> {
              Assert.assertEquals(
                  getSavedModelFamilyTypeDTO().getName(), savedModelFamilyTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateModelFamilyTypeService() {
    ModelFamilyTypeDTO updatedModelFamilyTypeDTO = getUpdatedModelFamilyTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedModelFamilyTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelFamilyTypeDTO());
    Mono<ModelFamilyTypeDTO> updatedModelFamilyType =
        modelFamilyTypeService.update(updatedModelFamilyTypeDTO);

    StepVerifier.create(updatedModelFamilyType)
        .expectNextMatches(
            updatedModelFamilyTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelFamilyTypeDTO().getName(), updatedModelFamilyTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelFamilyTypeService() {
    ModelFamilyTypeDTO updatedModelFamilyTypeDTO = getUpdatedModelFamilyTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedModelFamilyTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelFamilyTypeDTO());

    Mono<ModelFamilyTypeDTO> updatedModelFamilyType =
        modelFamilyTypeService.partialUpdate(updatedModelFamilyTypeDTO);

    StepVerifier.create(updatedModelFamilyType)
        .expectNextMatches(
            updatedModelFamilyTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelFamilyTypeDTO().getName(), updatedModelFamilyTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }
}
