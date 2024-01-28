package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.*;
import jakarta.transaction.Transactional;
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
public class ModelFacadeTest extends BasicFacadeTest {
  @Autowired private ModelFacade modelFacade;

  private final String EXISTING_MODEL_ID = "123e4567-e89b-12d3-a456-426614174001";
  private final String NON_EXISTING_MODEL_ID = UUID.randomUUID().toString();
  private final String ML_TASK_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private final String MODEL_STRUCTURE_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private final String MODEL_ENSEMBLE_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private final String MODEL_FAMILY_TYPE_ID = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  private ModelDTO getModel() {
    MlTaskTypeDTO mlTaskType = new MlTaskTypeDTO();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString(ML_TASK_TYPE_ID));
    ModelStructureTypeDTO modelStructureType = new ModelStructureTypeDTO();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString(MODEL_STRUCTURE_TYPE_ID));
    ModelEnsembleTypeDTO modelEnsembleType = new ModelEnsembleTypeDTO();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString(MODEL_ENSEMBLE_TYPE_ID));
    ModelFamilyTypeDTO modelFamilyType = new ModelFamilyTypeDTO();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString(MODEL_FAMILY_TYPE_ID));

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
    model.setDisplayName("Display1");

    return model;
  }

  private ModelDTO getUpdatedModel() {
    ModelDTO model = new ModelDTO();
    model.setId(UUID.fromString(EXISTING_MODEL_ID));
    MlTaskTypeDTO mlTaskType = new MlTaskTypeDTO();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString(ML_TASK_TYPE_ID));
    ModelStructureTypeDTO modelStructureType = new ModelStructureTypeDTO();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString(MODEL_STRUCTURE_TYPE_ID));
    ModelEnsembleTypeDTO modelEnsembleType = new ModelEnsembleTypeDTO();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString(MODEL_ENSEMBLE_TYPE_ID));
    ModelFamilyTypeDTO modelFamilyType = new ModelFamilyTypeDTO();
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

  private ModelDTO getPartialUpdatedModel() {
    ModelDTO model = new ModelDTO();
    model.setId(UUID.fromString(EXISTING_MODEL_ID));
    model.setName("partial_updated_test_model");
    return model;
  }

  @Test
  void testSaveModelFacade() {
    Mono<ModelDTO> savedModelDTO = modelFacade.save(getModel());

    savedModelDTO.subscribe(
        modelDTO -> {
          Assert.assertEquals(getModel().getName(), modelDTO.getName());
          modelFacade.delete(modelDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateModelFacade() {
    Mono<ModelDTO> updatedModelDTO = modelFacade.update(getUpdatedModel());

    updatedModelDTO.subscribe(
        modelDTO -> {
          Assert.assertEquals(getUpdatedModel().getName(), modelDTO.getName());
          Assert.assertEquals(getUpdatedModel().getId(), modelDTO.getId());
        });
  }

  @Test
  @Transactional
  void testPartialUpdateModelFacade() {
    Mono<ModelDTO> updatedModelDTO = modelFacade.partialUpdate(getPartialUpdatedModel());

    updatedModelDTO.subscribe(
        modelDTO -> {
          Assert.assertEquals(getPartialUpdatedModel().getName(), modelDTO.getName());
          Assert.assertEquals(getPartialUpdatedModel().getId(), modelDTO.getId());
          Assert.assertEquals(getModel().getDisplayName(), modelDTO.getDisplayName());
        });
  }

  @Test
  @Transactional
  void testFindByIdModelFacade() {
    Mono<ModelDTO> savedModelDTO = modelFacade.save(getModel());

    savedModelDTO.subscribe(
        modelDTO -> {
          Mono<ModelDTO> foundModelDTO = modelFacade.findOne(modelDTO.getId());
          foundModelDTO.subscribe(
              foundDTO -> {
                Assert.assertEquals(modelDTO.getId(), foundDTO.getId());
                Assert.assertEquals(modelDTO.getName(), foundDTO.getName());
              });
          modelFacade.delete(modelDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testExistsByIdModelFacade() {
    Mono<ModelDTO> savedModelDTO = modelFacade.save(getModel());

    savedModelDTO.subscribe(
        modelDTO -> {
          Mono<Boolean> exists = modelFacade.existsById(modelDTO.getId());
          StepVerifier.create(exists).expectNext(true).verifyComplete();
          modelFacade.delete(modelDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testFindByIdNotExistingModelFacade() {
    Mono<ModelDTO> modelMono = modelFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_ID));
    StepVerifier.create(modelMono).expectError(NoSuchElementException.class).verify();
  }

  @Test
  @Transactional
  void testExistsByIdNotExistingModelFacade() {
    Mono<Boolean> exists = modelFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_ID));
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
