package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ModelTypeServiceTest extends BasicServiceTest {

  // String IDs for testing
  private static final String EXISTING_MODEL_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_TYPE_ID = "123e4567-e89b-12d3-a456-426614174099";

  private static final String EXISTING_MODEL_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Autowired private ModelTypeService modelTypeService;

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
  void testFindAllModelTypeService() {
    Mono<List<ModelTypeDTO>> modelTypes = modelTypeService.findAll();

    List<ModelTypeDTO> modelTypeDTOS = modelTypes.block();

    Assert.assertNotNull(modelTypeDTOS);
    Assert.assertEquals(4, modelTypeDTOS.size());
    Assert.assertEquals("modeltype1", modelTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdModelTypeService() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_TYPE_ID);
    Mono<ModelTypeDTO> modelTypeMono = modelTypeService.findOne(existingId);

    StepVerifier.create(modelTypeMono)
        .expectNextMatches(
            modelTypeDTO -> {
              Assert.assertEquals(existingId, modelTypeDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_TYPE_ID);
    Mono<ModelTypeDTO> modelTypeMono = modelTypeService.findOne(nonExistingId);

    StepVerifier.create(modelTypeMono).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testUpdateModelTypeService() {
    Mono<ModelTypeDTO> updatedModelType = modelTypeService.update(getUpdatedModelTypeDTO());

    StepVerifier.create(updatedModelType)
        .expectNextMatches(
            modelTypeDTO -> {
              System.out.println("Updated ModelType: " + modelTypeDTO);
              Assert.assertEquals(getUpdatedModelTypeDTO().getName(), modelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelTypeService() {
    Mono<ModelTypeDTO> updatedModelType = modelTypeService.partialUpdate(getUpdatedModelTypeDTO());

    StepVerifier.create(updatedModelType)
        .expectNextMatches(
            modelTypeDTO -> {
              Assert.assertEquals(getUpdatedModelTypeDTO().getName(), modelTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdModelTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_TYPE_ID);
    Mono<Boolean> exists = modelTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_TYPE_ID);
    Mono<Boolean> exists = modelTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelTypeService() {
    Mono<ModelTypeDTO> savedModelTypeDTO = modelTypeService.save(getModelTypeDTO());
    savedModelTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getModelTypeDTO().getName(), modelTypeDTO.getName());
          modelTypeService.delete(modelTypeDTO.getId()).block();
        });
  }
}
