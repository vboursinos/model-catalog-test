package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
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
public class ModelGroupTypeServiceTest extends BasicServiceTest {
  @Autowired private ModelGroupTypeService modelGroupTypeService;

  private ModelGroupTypeDTO getModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setName("test_name");
    return modelGroupTypeDTO;
  }

  private ModelGroupTypeDTO getUpdatedModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
  void testFindByIdModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> modelGroupTypeDTOMono =
        modelGroupTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelGroupTypeDTOMono.subscribe(
        modelGroupTypeDTO -> {
          Assert.assertEquals("modelgroup1", modelGroupTypeDTO.getName());
        });
  }

  @Test
  void testSaveModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> savedModelGroupTypeDTO =
        modelGroupTypeService.save(getModelGroupTypeDTO());
    savedModelGroupTypeDTO.subscribe(
        modelGroupTypeDTO -> {
          Assert.assertEquals(getModelGroupTypeDTO().getName(), modelGroupTypeDTO.getName());
          modelGroupTypeService.delete(modelGroupTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelGroupTypeService() {
    Mono<ModelGroupTypeDTO> updatedModelGroupTypeDTO =
        modelGroupTypeService.save(getUpdatedModelGroupTypeDTO());
    updatedModelGroupTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelGroupTypeDTO().getName(), modelTypeDTO.getName());
        });
  }
}
