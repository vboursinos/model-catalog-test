package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTOTest {

  private ModelTO modelTO;

  @BeforeEach
  public void setUp() {
    modelTO = new ModelTO();
    modelTO.setId(UUID.randomUUID());
    modelTO.setName("TestModel");
    modelTO.setDisplayName("Test Model");
    modelTO.setDescription("Description of the test model");
    modelTO.setAdvantages(new String[] {"Advantage1", "Advantage2"});
    modelTO.setDisadvantages(new String[] {"Disadvantage1", "Disadvantage2"});
    modelTO.setEnabled(true);
    modelTO.setDecisionTree(true);

    Set<ModelGroupTypeTO> groups = new HashSet<>();
    ModelGroupTypeTO group1 = new ModelGroupTypeTO();
    group1.setId(UUID.randomUUID());
    group1.setName("Group1");
    groups.add(group1);
    modelTO.setGroups(groups);

    Set<MetricTO> incompatibleMetrics = new HashSet<>();
    MetricTO metric1 = new MetricTO();
    metric1.setId(UUID.randomUUID());
    metric1.setMetric("Metric1");
    incompatibleMetrics.add(metric1);
    modelTO.setIncompatibleMetrics(incompatibleMetrics);

    List<ParameterTO> parameters = new ArrayList<>();
    ParameterTO parameter1 = new ParameterTO();
    parameter1.setId(UUID.randomUUID());
    parameter1.setName("Parameter1");
    parameters.add(parameter1);
    modelTO.setParameters(parameters);

    MlTaskTypeTO mlTaskType = new MlTaskTypeTO();
    mlTaskType.setId(UUID.randomUUID());
    mlTaskType.setName("MLTask1");
    modelTO.setMlTask(mlTaskType);

    ModelStructureTypeTO structureType = new ModelStructureTypeTO();
    structureType.setId(UUID.randomUUID());
    structureType.setName("Structure1");
    modelTO.setStructure(structureType);

    Set<ModelTypeTO> modelTypes = new HashSet<>();
    ModelTypeTO modelType = new ModelTypeTO();
    modelType.setId(UUID.randomUUID());
    modelType.setName("Type1");
    modelTypes.add(modelType);
    modelTO.setTypes(modelTypes);

    ModelFamilyTypeTO familyType = new ModelFamilyTypeTO();
    familyType.setId(UUID.randomUUID());
    familyType.setName("Family1");
    modelTO.setFamilyType(familyType);

    ModelEnsembleTypeTO ensembleType = new ModelEnsembleTypeTO();
    ensembleType.setId(UUID.randomUUID());
    ensembleType.setName("Ensemble1");
    modelTO.setEnsembleType(ensembleType);
  }

  @Test
  public void testEquals() {
    ModelTO sameIdModelTO = new ModelTO();
    sameIdModelTO.setId(modelTO.getId());
    sameIdModelTO.setName("TestModel");

    ModelTO differentIdModelTO = new ModelTO();
    differentIdModelTO.setId(UUID.randomUUID());
    differentIdModelTO.setName("DifferentTestModel");

    assertEquals(modelTO, sameIdModelTO);
    assertNotEquals(modelTO, differentIdModelTO);
    assertNotEquals(modelTO, null);
  }

  @Test
  public void testHashCode() {
    ModelTO sameIdModelTO = new ModelTO();
    sameIdModelTO.setId(modelTO.getId());
    sameIdModelTO.setName("TestModel");

    ModelTO differentIdModelTO = new ModelTO();
    differentIdModelTO.setId(UUID.randomUUID());
    differentIdModelTO.setName("DifferentTestModel");

    assertEquals(modelTO.hashCode(), sameIdModelTO.hashCode());
    assertNotEquals(modelTO.hashCode(), differentIdModelTO.hashCode());
  }
}
