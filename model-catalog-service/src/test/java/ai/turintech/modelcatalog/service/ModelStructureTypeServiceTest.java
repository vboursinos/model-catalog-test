package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
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
public class ModelStructureTypeServiceTest extends BasicServiceTest {

  // String IDs for testing
  private static final String EXISTING_MODEL_STRUCTURE_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_MODEL_STRUCTURE_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  private static final String EXISTING_MODEL_STRUCTURE_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Autowired private ModelStructureTypeService modelStructureTypeService;

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

  @Test
  void testFindAllModelStructureTypeService() {
    Mono<List<ModelStructureTypeDTO>> modelStructureTypes = modelStructureTypeService.findAll();

    List<ModelStructureTypeDTO> modelStructureTypeDTOS = modelStructureTypes.block();

    Assert.assertNotNull(modelStructureTypeDTOS);
    Assert.assertEquals(4, modelStructureTypeDTOS.size());
    Assert.assertEquals("modelstructuretype1", modelStructureTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdModelStructureTypeService() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID);
    Mono<ModelStructureTypeDTO> modelStructureType = modelStructureTypeService.findOne(existingId);

    StepVerifier.create(modelStructureType)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              Assert.assertEquals(existingId, modelStructureTypeDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID);
    Mono<ModelStructureTypeDTO> modelStructureType =
        modelStructureTypeService.findOne(nonExistingId);

    StepVerifier.create(modelStructureType)
        .expectError(NoSuchElementException.class) // Expect a NoSuchElementException
        .verify();
  }

  @Test
  void testUpdateModelStructureTypeService() {
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
    Mono<ModelStructureTypeDTO> savedModelStructureTypeDTO =
        modelStructureTypeService.save(getModelStructureTypeDTO());
    savedModelStructureTypeDTO.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals(
              getModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
          modelStructureTypeService.delete(modelStructureTypeDTO.getId()).block();
        });
  }

  @Test
  void testExistsByIdModelStructureTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID);
    Mono<Boolean> exists = modelStructureTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelStructureTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID);
    Mono<Boolean> exists = modelStructureTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
