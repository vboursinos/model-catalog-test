package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.repository.ModelStructureTypeRepository;
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
public class ModelStructureTypeServiceTest {

  private static final String EXISTING_MODEL_STRUCTURE_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_STRUCTURE_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";
  private static final String EXISTING_MODEL_STRUCTURE_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Mock private ModelStructureTypeRepository repository;

  @Mock private MapperInterface<ModelStructureTypeDTO, ModelStructureType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ModelStructureTypeServiceImpl modelStructureTypeService;

  private ModelStructureTypeDTO getModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setName("test_name");
    return modelStructureTypeDTO;
  }

  private ModelStructureTypeDTO getUpdatedModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID_FOR_UPDATE));
    modelStructureTypeDTO.setName("test_updated_modelstructuretype");
    return modelStructureTypeDTO;
  }

  private ModelStructureTypeDTO getSavedModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID));
    modelStructureTypeDTO.setName("test_name");
    return modelStructureTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelStructureTypeService, "context", context);
    ReflectionTestUtils.setField(
        modelStructureTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(modelStructureTypeService, "repository", repository);
    ReflectionTestUtils.setField(modelStructureTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllModelStructureTypeService() {
    List<ModelStructureTypeDTO> modelStructureTypeDTOList = List.of(getModelStructureTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ModelStructureTypeDTO>, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelStructureTypeDTOList);

    Mono<List<ModelStructureTypeDTO>> modelStructureTypes = modelStructureTypeService.findAll();
    StepVerifier.create(modelStructureTypes).expectNext(modelStructureTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdModelStructureTypeService() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelStructureTypeDTO());

    Mono<ModelStructureTypeDTO> modelStructureType = modelStructureTypeService.findOne(existingId);

    StepVerifier.create(modelStructureType)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              Assert.assertEquals(
                  getModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelStructureTypeDTO> modelStructureType =
        modelStructureTypeService.findOne(nonExistingId);

    StepVerifier.create(modelStructureType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testUpdateModelStructureTypeService() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedModelStructureTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelStructureTypeDTO());

    Mono<ModelStructureTypeDTO> updatedModelStructureTypeDTO =
        modelStructureTypeService.update(getUpdatedModelStructureTypeDTO());

    StepVerifier.create(updatedModelStructureTypeDTO)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelStructureTypeService() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedModelStructureTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelStructureTypeDTO());

    Mono<ModelStructureTypeDTO> updatedModelStructureTypeDTO =
        modelStructureTypeService.partialUpdate(getUpdatedModelStructureTypeDTO());

    StepVerifier.create(updatedModelStructureTypeDTO)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testSaveModelStructureTypeService() {
    ModelStructureTypeDTO savedModelStructureTypeDTO = getSavedModelStructureTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getSavedModelStructureTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getSavedModelStructureTypeDTO());

    Mono<ModelStructureTypeDTO> savedModelStructureType =
        modelStructureTypeService.save(savedModelStructureTypeDTO);

    StepVerifier.create(savedModelStructureType)
        .expectNextMatches(
            savedModelStructureTypeDTO1 -> {
              Assert.assertEquals(
                  getModelStructureTypeDTO().getName(), savedModelStructureTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdModelStructureTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);

    Mono<Boolean> exists = modelStructureTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelStructureTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelStructureTypeDTO, ModelStructureType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);

    Mono<Boolean> exists = modelStructureTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
