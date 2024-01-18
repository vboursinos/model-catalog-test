package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.entity.*;
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
public class ModelServiceTest extends BasicServiceTest {
  @Autowired private ModelService modelService;

  private ModelDTO getModel() {
    MlTaskTypeDTO mlTaskType = new MlTaskTypeDTO();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelStructureTypeDTO modelStructureType = new ModelStructureTypeDTO();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelEnsembleTypeDTO modelEnsembleType = new ModelEnsembleTypeDTO();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29"));
    ModelFamilyTypeDTO modelFamilyType = new ModelFamilyTypeDTO();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));

    ModelDTO model = new ModelDTO();
    model.setName("test_model");
    model.setEnabled(true);
    model.setAdvantages(new String[] {"advantage1", "advantage2"});
    model.setDisadvantages(new String[] {"disadvantage1", "disadvantage2"});
    model.setMlTask(mlTaskType);
    model.setStructure(modelStructureType);
    model.setEnsembleType(modelEnsembleType);
    model.setFamilyType(modelFamilyType);
    model.setDecisionTree(true);
    model.setDisplayName("test_displayName");

    return model;
  }

  private ModelDTO getUpdatedModel() {
    ModelDTO model = new ModelDTO();
    model.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
    MlTaskTypeDTO mlTaskType = new MlTaskTypeDTO();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelStructureTypeDTO modelStructureType = new ModelStructureTypeDTO();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelEnsembleTypeDTO modelEnsembleType = new ModelEnsembleTypeDTO();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29"));
    ModelFamilyTypeDTO modelFamilyType = new ModelFamilyTypeDTO();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));

    model.setName("updated_test_model");
    model.setEnabled(true);
    model.setAdvantages(new String[] {"advantage1", "advantage2"});
    model.setDisadvantages(new String[] {"disadvantage1", "disadvantage2"});
    model.setMlTask(mlTaskType);
    model.setStructure(modelStructureType);
    model.setEnsembleType(modelEnsembleType);
    model.setFamilyType(modelFamilyType);
    model.setDecisionTree(true);
    model.setDisplayName("Display1");
    return model;
  }

  @Test
  void testSaveModelEnsembleTypeRepository() {
    Mono<ModelDTO> savedModelDTO = modelService.save(getModel());

    savedModelDTO.subscribe(
        modelDTO -> {
          Assert.assertEquals(getModel().getName(), modelDTO.getName());
          modelService.delete(modelDTO.getId()).block();
        });
  }
}
