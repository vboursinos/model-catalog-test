package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
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
public class ModelEnsembleTypeServiceTest extends BasicServiceTest {

  // String IDs for testing
  private static final String EXISTING_MODEL_ENSEMBLE_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_MODEL_ENSEMBLE_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Autowired private ModelEnsembleTypeService modelEnsembleTypeService;

  private ModelEnsembleTypeDTO getModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setName("test_name");
    return modelEnsembleTypeDTO;
  }

  private ModelEnsembleTypeDTO getUpdatedModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID_FOR_UPDATE));
    modelEnsembleTypeDTO.setName("test_updated_modelEnsembletype");
    return modelEnsembleTypeDTO;
  }

  @Test
  void testFindAllModelEnsembleTypeService() {
    Mono<List<ModelEnsembleTypeDTO>> modelEnsembleTypes = modelEnsembleTypeService.findAll();
    List<ModelEnsembleTypeDTO> modelEnsembleTypeDTOS = modelEnsembleTypes.block();

    Assert.assertNotNull(modelEnsembleTypeDTOS);
    Assert.assertEquals(4, modelEnsembleTypeDTOS.size());
    Assert.assertEquals("modelensembletype1", modelEnsembleTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID);
    Mono<ModelEnsembleTypeDTO> modelEnsembleType = modelEnsembleTypeService.findOne(existingId);

    StepVerifier.create(modelEnsembleType)
        .expectNextMatches(
            modelEnsembleTypeDTO -> {
              Assert.assertEquals("modelensembletype1", modelEnsembleTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID);
    Mono<ModelEnsembleTypeDTO> modelEnsembleType = modelEnsembleTypeService.findOne(nonExistingId);

    StepVerifier.create(modelEnsembleType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID);
    Mono<Boolean> existsForExistingId = modelEnsembleTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID);
    Mono<Boolean> existsForNonExistingId = modelEnsembleTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteModelEnsembleTypeService() {
    Mono<ModelEnsembleTypeDTO> savedModelEnsembleType =
        modelEnsembleTypeService.save(getModelEnsembleTypeDTO());

    StepVerifier.create(savedModelEnsembleType)
        .expectNextMatches(
            savedModelEnsembleTypeDTO -> {
              Assert.assertEquals(
                  getModelEnsembleTypeDTO().getName(), savedModelEnsembleTypeDTO.getName());
              return true;
            })
        .verifyComplete();

    // Verify deletion
    Mono<Void> deletion =
        savedModelEnsembleType.flatMap(
            modelEnsembleTypeDTO -> modelEnsembleTypeService.delete(modelEnsembleTypeDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  void testUpdateModelEnsembleTypeService() {
    Mono<ModelEnsembleTypeDTO> updatedModelEnsembleType =
        modelEnsembleTypeService.save(getUpdatedModelEnsembleTypeDTO());

    StepVerifier.create(updatedModelEnsembleType)
        .expectNextMatches(
            updatedModelEnsembleTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelEnsembleTypeDTO().getName(),
                  updatedModelEnsembleTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
