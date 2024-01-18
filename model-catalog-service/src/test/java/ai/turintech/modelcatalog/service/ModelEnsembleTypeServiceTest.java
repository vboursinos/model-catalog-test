package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
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
  void testFindByIdModelEnsembleTypeService() {
    Mono<ModelEnsembleTypeDTO> modelEnsembleTypeDTOMono =
        modelEnsembleTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelEnsembleTypeDTOMono.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals("modelensembletype1", modelEnsembleTypeDTO.getName());
        });
  }

  @Test
  void testSaveModelEnsembleTypeService() {
    Mono<ModelEnsembleTypeDTO> savedModelEnsembleTypeDTO =
        modelEnsembleTypeService.save(getModelEnsembleTypeDTO());
    savedModelEnsembleTypeDTO.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals(getModelEnsembleTypeDTO().getName(), modelEnsembleTypeDTO.getName());
          modelEnsembleTypeService.delete(modelEnsembleTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelEnsembleTypeService() {
    Mono<ModelEnsembleTypeDTO> updatedModelEnsembleTypeDTO =
        modelEnsembleTypeService.save(getUpdatedModelEnsembleTypeDTO());
    updatedModelEnsembleTypeDTO.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelEnsembleTypeDTO().getName(), modelEnsembleTypeDTO.getName());
        });
  }
}
