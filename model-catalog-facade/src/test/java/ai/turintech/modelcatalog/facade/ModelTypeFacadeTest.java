package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ModelTypeFacadeTest extends BasicFacadeTest {
  private final String EXISTING_MODEL_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private final String EXISTING_MODEL_TYPE_ID_FOR_UPDATE = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private final String NON_EXISTING_MODEL_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ModelTypeFacade modelTypeFacade;

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

  @Test
  void testFindAllModelTypeFacade() {
    Flux<ModelTypeDTO> modelTypes = modelTypeFacade.findAll();
    modelTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            modelTypeDTOS -> {
              assertEquals(
                  4, modelTypeDTOS.size(), "Returned group types do not match expected size");
            });
  }

  @Test
  void testFindByIdModelTypeFacade() {
    Mono<ModelTypeDTO> modelTypeDTOMono =
        modelTypeFacade.findOne(UUID.fromString(EXISTING_MODEL_TYPE_ID));

    modelTypeDTOMono.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals("modeltype1", modelTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelTypeFacade() {
    UUID existingModelTypeId = UUID.fromString(EXISTING_MODEL_TYPE_ID);

    Mono<Boolean> exists = modelTypeFacade.existsById(existingModelTypeId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingModelTypeFacade() {
    Mono<Boolean> exists = modelTypeFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_TYPE_ID));
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelTypeFacade() {
    Mono<ModelTypeDTO> savedModelTypeDTO = modelTypeFacade.save(getModelTypeDTO());
    savedModelTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getModelTypeDTO().getName(), modelTypeDTO.getName());
          modelTypeFacade.delete(modelTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelTypeFacade() {
    Mono<ModelTypeDTO> updatedModelTypeDTO = modelTypeFacade.update(getUpdatedModelTypeDTO());
    updatedModelTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testPartialUpdateModelTypeFacade() {
    Mono<ModelTypeDTO> updatedModelTypeDTO =
        modelTypeFacade.partialUpdate(getUpdatedModelTypeDTO());
    updatedModelTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelTypeFacade() {
    Mono<ModelTypeDTO> savedModelType = modelTypeFacade.save(getModelTypeDTO());

    savedModelType.subscribe(
        modelTypeDTO -> {
          Mono<Void> deleteResult = modelTypeFacade.delete(modelTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
                Mono<ModelTypeDTO> findResult = modelTypeFacade.findOne(modelTypeDTO.getId());
                findResult.subscribe(
                    notFoundModelTypeDTO -> Assert.assertNull(notFoundModelTypeDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelTypeFacade() {
    Mono<ModelTypeDTO> modelType =
        modelTypeFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_TYPE_ID));

    StepVerifier.create(modelType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testSaveAndUpdateModelTypeFacade() {
    // Save a model type first
    Mono<ModelTypeDTO> savedModelType = modelTypeFacade.save(getModelTypeDTO());

    savedModelType.subscribe(
        modelTypeDTO -> {
          // Update the saved model type
          ModelTypeDTO updatedModelTypeDTO = getUpdatedModelTypeDTO();
          updatedModelTypeDTO.setId(modelTypeDTO.getId());
          Mono<ModelTypeDTO> updatedModelTypeMono = modelTypeFacade.save(updatedModelTypeDTO);

          updatedModelTypeMono.subscribe(
              updatedModelType -> {
                Assert.assertEquals(updatedModelType.getName(), updatedModelTypeDTO.getName());
                // Clean up: Delete the updated model type
                modelTypeFacade.delete(updatedModelType.getId()).block();
              });
        });
  }
}
