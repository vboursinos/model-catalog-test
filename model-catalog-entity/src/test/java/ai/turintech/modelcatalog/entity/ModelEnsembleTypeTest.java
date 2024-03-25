package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ModelEnsembleTypeTest {

  private final ModelEnsembleType modelEnsembleType = new ModelEnsembleType();

  @Test
  public void testName() {
    String expectedName = "TestName";
    modelEnsembleType.name(expectedName);

    String actualName = modelEnsembleType.getName();

    assertNotNull(actualName, "Expected name to be not null");
    assertEquals(expectedName, actualName, "Expected and actual names should be the same");
  }

  @Test
  public void testSetAndGetModels() {
    Set<Model> modelSet = new HashSet<>();
    Model model = new Model();
    modelSet.add(model);

    modelEnsembleType.setModels(modelSet);

    Set<Model> actualValue = modelEnsembleType.getModels();

    assertNotNull(actualValue, "Expected actual value to not be null");
    assertEquals(modelSet, actualValue, "Expected and actual values should be equal");
  }

  @Test
  public void testAddModel() {
    Model model = new Model();

    modelEnsembleType.addModels(model);

    assertTrue(modelEnsembleType.getModels().contains(model), "Added Model should be present");
  }

  @Test
  public void testRemoveModel() {
    Model model = new Model();

    modelEnsembleType.addModels(model);
    modelEnsembleType.removeModels(model);

    assertFalse(
        modelEnsembleType.getModels().contains(model), "Removed Model should not be present");
  }

  @Test
  public void testHashCode() {
    ModelEnsembleType modelEnsembleType1 = new ModelEnsembleType();
    modelEnsembleType1.setName("Test EnsembleType");
    ModelEnsembleType modelEnsembleType2 = new ModelEnsembleType();
    modelEnsembleType2.setName("Test EnsembleType");
    assertEquals(modelEnsembleType1.hashCode(), modelEnsembleType2.hashCode());
  }

  @Test
  public void testToString() {
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("Test EnsembleType");
    assertTrue(modelEnsembleType.toString().contains("Test EnsembleType"));
  }
}
