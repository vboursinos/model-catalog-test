package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class MlTaskTypeServiceTest extends BasicServiceTest {
  @Autowired private MlTaskTypeService mlTaskTypeService;

  private MlTaskTypeDTO getMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setName("test_name");
    return mlTaskTypeDTO;
  }

  private MlTaskTypeDTO getUpdatedMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    mlTaskTypeDTO.setName("test_updated_mltasktype");
    return mlTaskTypeDTO;
  }

  @Test
  void testFindAllMlTaskTypeService() {
    Mono<List<MlTaskTypeDTO>> mlTaskTypes = mlTaskTypeService.findAll();

    mlTaskTypes.subscribe(
        mlTaskDTOList -> {
          Assert.assertTrue(mlTaskDTOList.size() == 4);
          Assert.assertTrue(mlTaskDTOList.get(0).getName().equals("mltask1"));
        });
  }

  @Test
  void testFindByIdMlTaksService() {
    Mono<MlTaskTypeDTO> mlTaskTypeDTOMono =
        mlTaskTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    mlTaskTypeDTOMono.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals("mltask1", mlTaskTypeDTO.getName());
        });
  }

  @Test
  void testSaveMlTaskTypeService() {
    Mono<MlTaskTypeDTO> savedMlTaskTypeDTO = mlTaskTypeService.save(getMlTaskTypeDTO());
    savedMlTaskTypeDTO.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals(getMlTaskTypeDTO().getName(), mlTaskTypeDTO.getName());
          mlTaskTypeService.delete(mlTaskTypeDTO.getId());
        });
  }

  @Test
  void testUpdateMlTaskTypeService() {
    Mono<MlTaskTypeDTO> updatedMlTaskTypeDTO = mlTaskTypeService.save(getUpdatedMlTaskTypeDTO());
    updatedMlTaskTypeDTO.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals(getUpdatedMlTaskTypeDTO().getName(), mlTaskTypeDTO.getName());
        });
  }
}
