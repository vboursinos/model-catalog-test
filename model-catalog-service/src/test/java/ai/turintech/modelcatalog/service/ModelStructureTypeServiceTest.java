package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
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
    Mono<ModelStructureTypeDTO> modelStructureTypeDTOMono =
        modelStructureTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelStructureTypeDTOMono.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals("modelstructuretype1", modelStructureTypeDTO.getName());
        });
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
  void testUpdateModelStructureTypeService() {
    Mono<ModelStructureTypeDTO> updatedModelStructureTypeDTO =
        modelStructureTypeService.save(getUpdatedModelStructureTypeDTO());
    updatedModelStructureTypeDTO.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
        });
  }
}
