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
  @Autowired private ModelEnsembleTypeService modelEnsembleTypeService;

  private ModelEnsembleTypeDTO getModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setName("test_name");
    return modelEnsembleTypeDTO;
  }

  private ModelEnsembleTypeDTO getUpdatedModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
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
    UUID nonExistingId = UUID.randomUUID();
    Mono<ModelEnsembleTypeDTO> modelEnsembleType = modelEnsembleTypeService.findOne(nonExistingId);

    StepVerifier.create(modelEnsembleType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
    Mono<Boolean> existsForExistingId = modelEnsembleTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID();
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
