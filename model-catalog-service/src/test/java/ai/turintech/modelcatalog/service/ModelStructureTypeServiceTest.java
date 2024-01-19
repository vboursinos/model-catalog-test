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
  @Autowired private ModelStructureTypeService modelStructureTypeService;

  private ModelStructureTypeDTO getModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setName("test_name");
    return modelStructureTypeDTO;
  }

  private ModelStructureTypeDTO getUpdatedModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
    Mono<ModelStructureTypeDTO> modelStructureType = modelStructureTypeService.findOne(existingId);

    StepVerifier.create(modelStructureType)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              System.out.println("Found ModelStructureType by ID: " + modelStructureTypeDTO);
              Assert.assertEquals(existingId, modelStructureTypeDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID(); // Assuming this ID does not exist in your data
    Mono<ModelStructureTypeDTO> modelStructureType =
        modelStructureTypeService.findOne(nonExistingId);

    StepVerifier.create(modelStructureType)
        .expectError(NoSuchElementException.class) // Expect a NoSuchElementException
        .verify();
  }

  @Test
  void testUpdateModelStructureTypeService() {
    Mono<ModelStructureTypeDTO> updatedModelStructureTypeDTO =
        modelStructureTypeService.save(getUpdatedModelStructureTypeDTO());

    StepVerifier.create(updatedModelStructureTypeDTO)
        .expectNextMatches(
            modelStructureTypeDTO -> {
              System.out.println("Updated ModelStructureType: " + modelStructureTypeDTO);
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
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");
    Mono<Boolean> exists = modelStructureTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdModelStructureTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID();
    Mono<Boolean> exists = modelStructureTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
