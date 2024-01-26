package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.entity.*;
import jakarta.transaction.Transactional;
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
public class ModelServiceTest extends BasicServiceTest {
  private static final String ML_TASK_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String MODEL_STRUCTURE_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String MODEL_ENSEMBLE_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String MODEL_FAMILY_TYPE_ID = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private static final String EXISTING_MODEL_ID = "123e4567-e89b-12d3-a456-426614174001";

  @Autowired private ModelService modelService;

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
    model.setDisplayName("test_displayName");

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

  @Test
  @Transactional
  void testSaveModelEnsembleTypeService() {
    Mono<ModelDTO> savedModelDTO = modelService.save(getModel());

    savedModelDTO.subscribe(
        modelDTO -> {
          Assert.assertEquals(getModel().getName(), modelDTO.getName());
          modelService.delete(modelDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testSaveModelService() {
    Mono<ModelDTO> savedModelDTO = modelService.save(getModel());

    StepVerifier.create(savedModelDTO)
        .expectNextMatches(
            modelDTO -> {
              Assert.assertEquals(getModel().getName(), modelDTO.getName());
              return true;
            })
        .verifyComplete();

    Mono<Void> deletion = savedModelDTO.flatMap(modelDTO -> modelService.delete(modelDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  @Transactional
  void testUpdateModelService() {
    Mono<ModelDTO> savedModel = modelService.save(getModel());

    Mono<ModelDTO> updatedModel =
        savedModel.flatMap(
            initialModel -> {
              ModelDTO updatedModelDTO = getUpdatedModel();
              updatedModelDTO.setId(initialModel.getId());
              return modelService.save(updatedModelDTO);
            });

    StepVerifier.create(updatedModel)
        .expectNextMatches(
            updatedModelDTO -> {
              Assert.assertEquals(getUpdatedModel().getName(), updatedModelDTO.getName());
              return true;
            })
        .verifyComplete();

    Mono<Void> deletion = updatedModel.flatMap(modelDTO -> modelService.delete(modelDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  @Transactional
  void testFindByIdModelService() {
    UUID modelId = modelService.save(getModel()).block().getId();

    Mono<ModelDTO> foundModel = modelService.findOne(modelId);

    StepVerifier.create(foundModel)
        .expectNextMatches(
            modelDTO -> {
              Assert.assertEquals(getModel().getName(), modelDTO.getName());
              return true;
            })
        .verifyComplete();

    Mono<Void> deletion = foundModel.flatMap(modelDTO -> modelService.delete(modelDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdModelService() {
    UUID modelId = modelService.save(getModel()).block().getId();

    Mono<Boolean> exists = modelService.existsById(modelId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();

    Mono<Void> deletion = modelService.delete(modelId);

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdNotExistingModelService() {
    UUID existingModelId = modelService.save(getModel()).block().getId();
    UUID nonExistingModelId = UUID.randomUUID(); // Assuming this ID does not exist

    Mono<Boolean> existsForExistingId = modelService.existsById(existingModelId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();

    Mono<Boolean> existsForNonExistingId = modelService.existsById(nonExistingModelId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();

    Mono<Void> deletion = modelService.delete(existingModelId);

    StepVerifier.create(deletion).verifyComplete();
  }
}
