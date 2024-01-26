package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
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
public class MlTaskTypeServiceTest extends BasicServiceTest {

  // String IDs for testing
  private static final String EXISTING_ML_TASK_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_ML_TASK_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String NON_EXISTING_ML_TASK_TYPE_ID = "123e4567-e89b-12d3-a456-426614174099";

  @Autowired private MlTaskTypeService mlTaskTypeService;

  private MlTaskTypeDTO getMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setName("test_name");
    return mlTaskTypeDTO;
  }

  private MlTaskTypeDTO getUpdatedMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.fromString(EXISTING_ML_TASK_TYPE_ID_FOR_UPDATE));
    mlTaskTypeDTO.setName("test_updated_mltasktype");
    return mlTaskTypeDTO;
  }

  @Test
  void testFindAllMlTaskTypeService() {
    Mono<List<MlTaskTypeDTO>> mlTaskTypes = mlTaskTypeService.findAll();

    List<MlTaskTypeDTO> mlTaskDTOList = mlTaskTypes.block();

    Assert.assertNotNull(mlTaskDTOList);
    Assert.assertEquals(4, mlTaskDTOList.size());
    Assert.assertEquals("mltask1", mlTaskDTOList.get(0).getName());
  }

  @Test
  void testFindByIdMlTaskTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_ML_TASK_TYPE_ID);
    Mono<MlTaskTypeDTO> mlTaskType = mlTaskTypeService.findOne(existingId);

    StepVerifier.create(mlTaskType)
        .expectNextMatches(
            mlTaskTypeDTO -> {
              Assert.assertEquals("mltask1", mlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdMlTaskTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_ML_TASK_TYPE_ID);
    Mono<MlTaskTypeDTO> mlTaskType = mlTaskTypeService.findOne(nonExistingId);

    StepVerifier.create(mlTaskType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdMlTaskTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_ML_TASK_TYPE_ID);
    Mono<Boolean> existsForExistingId = mlTaskTypeService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdMlTaskTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_ML_TASK_TYPE_ID);
    Mono<Boolean> existsForNonExistingId = mlTaskTypeService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteMlTaskTypeService() {
    Mono<MlTaskTypeDTO> savedMlTaskType = mlTaskTypeService.save(getMlTaskTypeDTO());

    StepVerifier.create(savedMlTaskType)
        .expectNextMatches(
            savedMlTaskTypeDTO -> {
              Assert.assertEquals(getMlTaskTypeDTO().getName(), savedMlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();

    // Verify deletion
    Mono<Void> deletion =
        savedMlTaskType.flatMap(mlTaskTypeDTO -> mlTaskTypeService.delete(mlTaskTypeDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  void testUpdateMlTaskTypeService() {
    Mono<MlTaskTypeDTO> updatedMlTaskType = mlTaskTypeService.save(getUpdatedMlTaskTypeDTO());

    StepVerifier.create(updatedMlTaskType)
        .expectNextMatches(
            updatedMlTaskTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedMlTaskTypeDTO().getName(), updatedMlTaskTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
