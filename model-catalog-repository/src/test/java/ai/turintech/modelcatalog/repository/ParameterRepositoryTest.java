package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterRepositoryTest extends BasicRepositoryTest {
  @Autowired private ParameterRepository parameterRepository;

  private Parameter getParameter() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29"));
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));

    Model model = new Model();
    model.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174002"));
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

    Parameter parameter = new Parameter();
    parameter.setName("test_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setModel(model);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    return parameter;
  }

  private Parameter getUpdatedParameter() {
    Model model = new Model();
    model.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("mltask1");
    mlTaskType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setName("modelstructuretype1");
    modelStructureType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("modelensembletype3");
    modelEnsembleType.setId(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29"));
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setName("modelfamilytype4");
    modelFamilyType.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));

    model.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174002"));
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

    Parameter parameter = new Parameter();
    parameter.setName("update_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setModel(model);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    return parameter;
  }

  @Test
  void testFindAllParameterRepository() {
    List<Parameter> parameters = parameterRepository.findAll();
    Assertions.assertEquals(1, parameters.size());
  }

  @Test
  void testFindByIdParameterRepository() {
    Parameter parameter =
        parameterRepository.findById(UUID.fromString("523e4567-e89b-12d3-a456-426614174001")).get();
    Assertions.assertEquals("parameter_name", parameter.getName());
  }

  @Test
  void testSaveParameterRepository() {
    Parameter savedParameter = parameterRepository.save(getParameter());
    Assertions.assertEquals(getParameter().getName(), savedParameter.getName());
    parameterRepository.delete(savedParameter);
  }

  @Test
  void testUpdateParameterRepository() {
    Parameter updateParameter = parameterRepository.save(getUpdatedParameter());
    Assertions.assertEquals(getUpdatedParameter().getName(), updateParameter.getName());
  }
}
