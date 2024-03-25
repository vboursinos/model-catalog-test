package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelDTOTest {

  private ModelDTO modelDTO;

  @BeforeAll
  public void setUp() {
    modelDTO = new ModelDTO();
    modelDTO.setId(UUID.randomUUID());
    modelDTO.setName("TestModel");
    modelDTO.setDisplayName("Test Model");
    modelDTO.setDescription("Description");
    modelDTO.setAdvantages(new String[] {"Advantage1", "Advantage2"});
    modelDTO.setDisadvantages(new String[] {"Disadvantage1", "Disadvantage2"});
    modelDTO.setEnabled(true);
    modelDTO.setDecisionTree(true);

    Set<ModelGroupTypeDTO> groups = new HashSet<>();
    modelDTO.setGroups(groups);

    Set<MetricDTO> incompatibleMetrics = new HashSet<>();
    modelDTO.setIncompatibleMetrics(incompatibleMetrics);

    List<ParameterDTO> parameters = List.of(new ParameterDTO());
    modelDTO.setParameters(parameters);

    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    modelDTO.setMlTask(mlTaskTypeDTO);

    ModelStructureTypeDTO structureTypeDTO = new ModelStructureTypeDTO();
    modelDTO.setStructure(structureTypeDTO);

    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelDTO.setType(modelTypeDTO);

    ModelFamilyTypeDTO familyTypeDTO = new ModelFamilyTypeDTO();
    modelDTO.setFamilyType(familyTypeDTO);

    ModelEnsembleTypeDTO ensembleTypeDTO = new ModelEnsembleTypeDTO();
    modelDTO.setEnsembleType(ensembleTypeDTO);
  }

  @Test
  public void testEquals() {
    ModelDTO sameIdModelDTO = new ModelDTO();
    sameIdModelDTO.setId(modelDTO.getId());

    ModelDTO differentIdModelDTO = new ModelDTO();
    differentIdModelDTO.setId(UUID.randomUUID());

    assertEquals(modelDTO, sameIdModelDTO);
    assertNotEquals(modelDTO, differentIdModelDTO);
    assertNotEquals(modelDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelDTO sameIdModelDTO = new ModelDTO();
    sameIdModelDTO.setId(modelDTO.getId());

    ModelDTO differentIdModelDTO = new ModelDTO();
    differentIdModelDTO.setId(UUID.randomUUID());

    assertEquals(modelDTO.hashCode(), sameIdModelDTO.hashCode());
    assertNotEquals(modelDTO.hashCode(), differentIdModelDTO.hashCode());
  }

  @Test
  public void testGetSetName() {
    ModelDTO newModelDTO = new ModelDTO();
    newModelDTO.setName("NewTestModel");

    assertEquals("NewTestModel", newModelDTO.getName());
  }

  @Test
  public void testGetSetDisplayName() {
    ModelDTO newModelDTO = new ModelDTO();
    newModelDTO.setDisplayName("NewTest Model");

    assertEquals("NewTest Model", newModelDTO.getDisplayName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelDTO.equals(differentObject));
  }
}
