package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.repository.ModelGroupTypeRepository;
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
public class ModelGroupTypeServiceTest {

  private static final String EXISTING_MODEL_GROUP_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_GROUP_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";
  private static final String EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Mock private ModelGroupTypeRepository repository;

  @Mock private MapperInterface<ModelGroupTypeDTO, ModelGroupType> mapperInterface;

  @Mock private ApplicationContext context;

  @InjectMocks private ModelGroupTypeServiceImpl modelGroupTypeService;

  private ModelGroupTypeDTO getModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setName("test_name");
    return modelGroupTypeDTO;
  }

  private ModelGroupTypeDTO getUpdatedModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE));
    modelGroupTypeDTO.setName("test_updated_modelgrouptype");
    return modelGroupTypeDTO;
  }

  private ModelGroupTypeDTO getSavedModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE));
    modelGroupTypeDTO.setName("test_name");
    return modelGroupTypeDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelGroupTypeService, "context", context);
    ReflectionTestUtils.setField(modelGroupTypeService, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(modelGroupTypeService, "repository", repository);
    ReflectionTestUtils.setField(modelGroupTypeService, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllModelGroupTypeService() {
    List<ModelGroupTypeDTO> modelGroupTypeDTOList = List.of(getModelGroupTypeDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            List<ModelGroupTypeDTO>, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(modelGroupTypeDTOList);

    Mono<List<ModelGroupTypeDTO>> modelGroupTypes = modelGroupTypeService.findAll();
    StepVerifier.create(modelGroupTypes).expectNext(modelGroupTypeDTOList).verifyComplete();
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelGroupTypeDTO());

    Mono<ModelGroupTypeDTO> modelGroupType = modelGroupTypeService.findOne(existingId);

    StepVerifier.create(modelGroupType)
        .expectNextMatches(
            modelGroupTypeDTO -> {
              Assert.assertEquals("test_name", modelGroupTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelGroupTypeDTO> modelGroupType = modelGroupTypeService.findOne(nonExistingId);

    StepVerifier.create(modelGroupType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.TRUE);
    Mono<Boolean> existsForExistingId = modelGroupTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(Boolean.FALSE);
    Mono<Boolean> existsForNonExistingId = modelGroupTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteModelGroupTypeService() {
    ModelGroupTypeDTO savedModelGroupTypeDTO = getSavedModelGroupTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getSavedModelGroupTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getSavedModelGroupTypeDTO());

    Mono<ModelGroupTypeDTO> savedModelGroupType =
        modelGroupTypeService.save(savedModelGroupTypeDTO);

    StepVerifier.create(savedModelGroupType)
        .expectNextMatches(
            savedModelGroupTypeDTO1 -> {
              Assert.assertEquals(
                  getModelGroupTypeDTO().getName(), savedModelGroupTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateModelGroupTypeService() {
    ModelGroupTypeDTO updatedModelGroupTypeDTO = getUpdatedModelGroupTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedModelGroupTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelGroupTypeDTO());
    Mono<ModelGroupTypeDTO> updatedModelGroupType =
        modelGroupTypeService.update(updatedModelGroupTypeDTO);

    StepVerifier.create(updatedModelGroupType)
        .expectNextMatches(
            updatedModelGroupTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelGroupTypeDTO().getName(), updatedModelGroupTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelGroupTypeService() {
    ModelGroupTypeDTO updatedModelGroupTypeDTO = getUpdatedModelGroupTypeDTO();

    ReactiveAbstractUUIDIdentityCrudCallableImpl<
            ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType>
        callable = mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedModelGroupTypeDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelGroupTypeDTO());

    Mono<ModelGroupTypeDTO> updatedModelGroupType =
        modelGroupTypeService.partialUpdate(updatedModelGroupTypeDTO);

    StepVerifier.create(updatedModelGroupType)
        .expectNextMatches(
            updatedModelGroupTypeDTO1 -> {
              Assert.assertEquals(
                  getUpdatedModelGroupTypeDTO().getName(), updatedModelGroupTypeDTO1.getName());
              return true;
            })
        .verifyComplete();
  }
}
