package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
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

    modelFamilyTypes.subscribe(
        modelFamilyTypeDTOS -> {
          Assert.assertTrue(modelFamilyTypeDTOS.size() == 4);
          Assert.assertTrue(modelFamilyTypeDTOS.get(0).getName().equals("modelfamilytype1"));
        });
  }

  @Test
  void testFindByIdModelFamilyTypeService() {
    Mono<ModelFamilyTypeDTO> modelFamilyTypeDTOMono =
        modelFamilyTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelFamilyTypeDTOMono.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals("modelfamilytype1", modelFamilyTypeDTO.getName());
        });
  }

  @Test
  void testSaveModelFamilyTypeService() {
    Mono<ModelFamilyTypeDTO> savedModelFamilyTypeDTO =
        modelFamilyTypeService.save(getModelFamilyTypeDTO());
    savedModelFamilyTypeDTO.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals(getModelFamilyTypeDTO().getName(), modelFamilyTypeDTO.getName());
          modelFamilyTypeService.delete(modelFamilyTypeDTO.getId());
        });
  }

  @Test
  void testUpdateModelFamilyTypeService() {
    Mono<ModelFamilyTypeDTO> updatedModelFamilyTypeDTO =
        modelFamilyTypeService.save(getUpdatedModelFamilyTypeDTO());
    updatedModelFamilyTypeDTO.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelFamilyTypeDTO().getName(), modelFamilyTypeDTO.getName());
        });
  }
}
