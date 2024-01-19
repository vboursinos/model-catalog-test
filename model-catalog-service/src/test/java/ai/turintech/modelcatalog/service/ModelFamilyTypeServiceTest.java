package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
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
public class ModelFamilyTypeServiceTest extends BasicServiceTest {
  @Autowired private ModelFamilyTypeService modelFamilyTypeService;

  private ModelFamilyTypeDTO getModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setName("test_name");
    return modelFamilyTypeDTO;
  }

  private ModelFamilyTypeDTO getUpdatedModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    modelFamilyTypeDTO.setName("test_updated_modelfamilytype");
    return modelFamilyTypeDTO;
  }

  @Test
  void testFindAllModelFamilyTypeService() {
    Mono<List<ModelFamilyTypeDTO>> modelFamilyTypes = modelFamilyTypeService.findAll();

    List<ModelFamilyTypeDTO> modelFamilyTypeDTOS = modelFamilyTypes.block();

    Assert.assertNotNull(modelFamilyTypeDTOS);
    Assert.assertEquals(4, modelFamilyTypeDTOS.size());
    Assert.assertEquals("modelfamilytype1", modelFamilyTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
    Mono<ModelFamilyTypeDTO> modelFamilyType = modelFamilyTypeService.findOne(existingId);

    StepVerifier.create(modelFamilyType)
        .expectNextMatches(
            modelFamilyTypeDTO -> {
              Assert.assertEquals("modelfamilytype1", modelFamilyTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID();
    Mono<ModelFamilyTypeDTO> modelFamilyType = modelFamilyTypeService.findOne(nonExistingId);

    StepVerifier.create(modelFamilyType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
    Mono<Boolean> existsForExistingId = modelFamilyTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID();
    Mono<Boolean> existsForNonExistingId = modelFamilyTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelFamilyTypeService() {
    Mono<ModelFamilyTypeDTO> savedModelFamilyTypeDTO =
        modelFamilyTypeService.save(getModelFamilyTypeDTO());
    savedModelFamilyTypeDTO.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals(getModelFamilyTypeDTO().getName(), modelFamilyTypeDTO.getName());
          modelFamilyTypeService.delete(modelFamilyTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelFamilyTypeService() {
    Mono<ModelFamilyTypeDTO> updatedModelFamilyType =
        modelFamilyTypeService.save(getUpdatedModelFamilyTypeDTO());

    StepVerifier.create(updatedModelFamilyType)
        .expectNextMatches(
            updatedModelFamilyTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedModelFamilyTypeDTO().getName(), updatedModelFamilyTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
