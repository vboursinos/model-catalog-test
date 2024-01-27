package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
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
public class ModelGroupTypeServiceTest extends BasicServiceTest {

  // String IDs for testing
  private static final String EXISTING_MODEL_GROUP_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_GROUP_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  private static final String EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Autowired private ModelGroupTypeService modelGroupTypeService;

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

  @Test
  void testFindAllModelGroupTypeService() {
    Mono<List<ModelGroupTypeDTO>> modelGroupTypes = modelGroupTypeService.findAll();

    List<ModelGroupTypeDTO> modelGroupTypeDTOS = modelGroupTypes.block();

    Assert.assertNotNull(modelGroupTypeDTOS);
    Assert.assertEquals(4, modelGroupTypeDTOS.size());
    Assert.assertEquals("modelgroup1", modelGroupTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID);
    Mono<ModelGroupTypeDTO> modelGroupType = modelGroupTypeService.findOne(existingId);

    StepVerifier.create(modelGroupType)
        .expectNextMatches(
            modelGroupTypeDTO -> {
              Assert.assertEquals("modelgroup1", modelGroupTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID);
    Mono<ModelGroupTypeDTO> modelGroupType = modelGroupTypeService.findOne(nonExistingId);

    StepVerifier.create(modelGroupType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID);
    Mono<Boolean> existsForExistingId = modelGroupTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID);
    Mono<Boolean> existsForNonExistingId = modelGroupTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> savedModelGroupType =
        modelGroupTypeService.save(getModelGroupTypeDTO());

    StepVerifier.create(savedModelGroupType)
        .expectNextMatches(
            savedModelGroupTypeDTO -> {
              Assert.assertEquals(
                  getModelGroupTypeDTO().getName(), savedModelGroupTypeDTO.getName());
              return true;
            })
        .verifyComplete();

    // Verify deletion
    Mono<Void> deletion =
        savedModelGroupType.flatMap(
            modelGroupTypeDTO -> modelGroupTypeService.delete(modelGroupTypeDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  void testUpdateModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> updatedModelGroupType =
        modelGroupTypeService.update(getUpdatedModelGroupTypeDTO());

    StepVerifier.create(updatedModelGroupType)
        .expectNextMatches(
            updatedModelGroupTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelGroupTypeDTO().getName(), updatedModelGroupTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> updatedModelGroupType =
        modelGroupTypeService.partialUpdate(getUpdatedModelGroupTypeDTO());

    StepVerifier.create(updatedModelGroupType)
        .expectNextMatches(
            updatedModelGroupTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelGroupTypeDTO().getName(), updatedModelGroupTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
