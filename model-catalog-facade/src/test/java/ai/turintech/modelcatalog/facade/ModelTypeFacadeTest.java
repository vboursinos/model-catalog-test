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
  @Autowired private ModelTypeFacade modelTypeFacade;

  private ModelTypeDTO getModelTypeDTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setName("test_name");
    return modelTypeDTO;
  }

  private ModelTypeDTO getUpdatedModelTypeDTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
        modelTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelTypeDTOMono.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals("modeltype1", modelTypeDTO.getName());
        });
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
    Mono<ModelTypeDTO> updatedModelTypeDTO = modelTypeFacade.save(getUpdatedModelTypeDTO());
    updatedModelTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelTypeFacade() {
    // Save a model type first
    Mono<ModelTypeDTO> savedModelType = modelTypeFacade.save(getModelTypeDTO());

    // Subscribe and delete the saved model type
    savedModelType.subscribe(
        modelTypeDTO -> {
          Mono<Void> deleteResult = modelTypeFacade.delete(modelTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted model type by ID
                Mono<ModelTypeDTO> findResult = modelTypeFacade.findOne(modelTypeDTO.getId());
                findResult.subscribe(
                    notFoundModelTypeDTO -> Assert.assertNull(notFoundModelTypeDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelTypeFacade() {
    // Try to find a model type by a non-existing ID
    Mono<ModelTypeDTO> modelType = modelTypeFacade.findOne(UUID.randomUUID());

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
