package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudCallable;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.repository.ModelRepository;
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
public class ModelServiceTest {

  private static final String EXISTING_MODEL_ID = "123e4567-e89b-12d3-a456-426614174001";
  private static final String NON_EXISTING_MODEL_ID = "123e4567-e89b-12d3-a456-426614174099";

  @Mock private ApplicationContext context;
  @Mock private ModelRepository repository;
  @Mock private MapperInterface<ModelDTO, Model> mapperInterface;

  @Mock private ModelMapper modelMapper;
  @InjectMocks private ModelServiceImpl modelServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(modelServiceImpl, "context", context);
    ReflectionTestUtils.setField(modelServiceImpl, "jdbcScheduler", Schedulers.immediate());
    //    ReflectionTestUtils.setField(modelServiceImpl, "repository", repository);
    //    ReflectionTestUtils.setField(modelServiceImpl, "mapperInterface", mapperInterface);
  }

  private ModelDTO getModelDTO() {
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setName("test_name");
    return modelDTO;
  }

  private ModelDTO getUpdatedModelDTO() {
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setId(UUID.fromString(EXISTING_MODEL_ID));
    modelDTO.setName("test_updated_model");
    return modelDTO;
  }

  private ModelDTO getSavedModelDTO() {
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setId(UUID.fromString(EXISTING_MODEL_ID));
    modelDTO.setName("test_save_model");
    return modelDTO;
  }

  @Test
  void testFindByIdModelServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelDTO, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getModelDTO());

    Mono<ModelDTO> model = modelServiceImpl.findOne(existingId);

    StepVerifier.create(model)
        .expectNextMatches(
            modelDTO -> {
              Assert.assertEquals("test_name", modelDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdModelServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelDTO, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<ModelDTO> model = modelServiceImpl.findOne(nonExistingId);

    StepVerifier.create(model).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdModelServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(true);

    Mono<Boolean> existsForExistingId = modelServiceImpl.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(false);

    Mono<Boolean> existsForNonExistingId = modelServiceImpl.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelDTO, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("create"),
            eq(getSavedModelDTO()),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getSavedModelDTO());

    Mono<ModelDTO> savedModel = modelServiceImpl.save(getSavedModelDTO());

    StepVerifier.create(savedModel)
        .expectNextMatches(
            savedModelDTO -> {
              Assert.assertEquals(getSavedModelDTO().getName(), savedModelDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateModelService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelDTO, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("update"),
            eq(getUpdatedModelDTO()),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelDTO());

    Mono<ModelDTO> updatedModel = modelServiceImpl.update(getUpdatedModelDTO());

    StepVerifier.create(updatedModel)
        .expectNextMatches(
            updatedModelDTO -> {
              Assert.assertEquals(getUpdatedModelDTO().getName(), updatedModelDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<ModelDTO, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("partialUpdate"),
            eq(getUpdatedModelDTO()),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedModelDTO());

    Mono<ModelDTO> partialUpdatedModel = modelServiceImpl.partialUpdate(getUpdatedModelDTO());

    StepVerifier.create(partialUpdatedModel)
        .expectNextMatches(
            partialUpdatedModelDTO -> {
              Assert.assertEquals(getUpdatedModelDTO().getName(), partialUpdatedModelDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testDeleteModelService() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Void, ModelDTO, Model> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveUUIDIdentityCrudCallable.class),
            eq("delete"),
            eq(existingId),
            eq(repository),
            eq(modelMapper)))
        .thenReturn(callable);
    modelServiceImpl.delete(existingId);
  }
}
