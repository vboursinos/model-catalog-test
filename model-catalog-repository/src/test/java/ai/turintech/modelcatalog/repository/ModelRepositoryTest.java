package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class ModelRepositoryTest {
  @Autowired private ModelRepository modelRepository;

  private static final String MODEL_ID = "123e4567-e89b-12d3-a456-426614174001";
  private static final String ML_TASK_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String MODEL_STRUCTURE_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String MODEL_ENSEMBLE_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String MODEL_FAMILY_TYPE_ID = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  private Model getModel() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString(ML_TASK_TYPE_ID));
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString(MODEL_STRUCTURE_TYPE_ID));
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString(MODEL_ENSEMBLE_TYPE_ID));
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString(MODEL_FAMILY_TYPE_ID));

    Model model = new Model();
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

  private Model getUpdatedModel() {
    Model model = new Model();
    model.setId(UUID.fromString(MODEL_ID));
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString(ML_TASK_TYPE_ID));
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString(MODEL_STRUCTURE_TYPE_ID));
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString(MODEL_ENSEMBLE_TYPE_ID));
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString(MODEL_FAMILY_TYPE_ID));

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
  void testFindAllModelRepository() {
    List<Model> models = modelRepository.findAll();
    Assertions.assertEquals(2, models.size());
  }

  @Test
  void testFindByIdModelRepository() {
    Model model =
        modelRepository
            .findById(UUID.fromString(MODEL_ID))
            .orElseThrow(() -> new NoSuchElementException("Model not found"));
    Assertions.assertEquals("Display1", model.getDisplayName());
  }

  @Test
  void testSaveModelEnsembleTypeRepository() {
    Model savedModel = modelRepository.save(getModel());
    Assertions.assertEquals(getModel().getName(), savedModel.getName());
    modelRepository.delete(savedModel);
  }

  @Test
  void testUpdateModelEnsembleTypeRepository() {
    Model updatedModel = modelRepository.save(getUpdatedModel());
    Assertions.assertEquals(getUpdatedModel().getName(), updatedModel.getName());
  }
}
