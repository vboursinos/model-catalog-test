package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.*;

class MlTaskTypeTest {

  private final MlTaskType mlTaskType = new MlTaskType();

  @Test
  public void testName() {
    String expectedName = "TestName";
    mlTaskType.name(expectedName);

    String actualName = mlTaskType.getName();

    assertNotNull(actualName, "Expected name to be not null");
    assertEquals(expectedName, actualName, "Expected and actual names should be the same");
  }

  @Test
  public void testSetAndGetModels() {
    Set<Model> modelSet = new HashSet<>();
    Model model = new Model();
    modelSet.add(model);

    mlTaskType.setModels(modelSet);

    Set<Model> actualValue = mlTaskType.getModels();

    assertNotNull(actualValue, "Expected actual value to not be null");
    assertEquals(modelSet, actualValue, "Expected and actual values should be equal");
  }

  @Test
  public void testAddModel() {
    Model model = new Model();

    mlTaskType.addModels(model);

    assertTrue(mlTaskType.getModels().contains(model), "Added Model should be present");
  }

  @Test
  public void testRemoveModel() {
    Model model = new Model();

    mlTaskType.addModels(model);
    mlTaskType.removeModels(model);

    assertFalse(mlTaskType.getModels().contains(model), "Removed Model should not be present");
  }

  @Test
  public void testHashCode() {
    MlTaskType mlTaskType1 = new MlTaskType();
    mlTaskType1.setName("Test TaskType");
    MlTaskType mlTaskType2 = new MlTaskType();
    mlTaskType2.setName("Test TaskType");
    assertEquals(mlTaskType1.hashCode(), mlTaskType2.hashCode());
  }

  @Test
  public void testToString() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("Test TaskType");
    assertTrue(mlTaskType.toString().contains("Test TaskType"));
  }
}
