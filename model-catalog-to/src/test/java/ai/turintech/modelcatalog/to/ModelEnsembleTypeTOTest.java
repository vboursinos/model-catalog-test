package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelEnsembleTypeTOTest {

  private ModelEnsembleTypeTO modelEnsembleTypeTO;

  @BeforeEach
  public void setUp() {
    modelEnsembleTypeTO = new ModelEnsembleTypeTO();
    modelEnsembleTypeTO.setId(UUID.randomUUID());
    modelEnsembleTypeTO.setName("TestModelEnsembleType");
  }

  @Test
  public void testEquals() {
    ModelEnsembleTypeTO sameIdModelEnsembleTypeTO = new ModelEnsembleTypeTO();
    sameIdModelEnsembleTypeTO.setId(modelEnsembleTypeTO.getId());
    sameIdModelEnsembleTypeTO.setName("TestModelEnsembleType");

    ModelEnsembleTypeTO differentIdModelEnsembleTypeTO = new ModelEnsembleTypeTO();
    differentIdModelEnsembleTypeTO.setId(UUID.randomUUID());
    differentIdModelEnsembleTypeTO.setName("DifferentTestModelEnsembleType");

    assertEquals(modelEnsembleTypeTO, sameIdModelEnsembleTypeTO);
    assertNotEquals(modelEnsembleTypeTO, differentIdModelEnsembleTypeTO);
    assertNotEquals(modelEnsembleTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ModelEnsembleTypeTO sameIdModelEnsembleTypeTO = new ModelEnsembleTypeTO();
    sameIdModelEnsembleTypeTO.setId(modelEnsembleTypeTO.getId());
    sameIdModelEnsembleTypeTO.setName("TestModelEnsembleType");

    ModelEnsembleTypeTO differentIdModelEnsembleTypeTO = new ModelEnsembleTypeTO();
    differentIdModelEnsembleTypeTO.setId(UUID.randomUUID());
    differentIdModelEnsembleTypeTO.setName("DifferentTestModelEnsembleType");

    assertEquals(modelEnsembleTypeTO.hashCode(), sameIdModelEnsembleTypeTO.hashCode());
    assertNotEquals(modelEnsembleTypeTO.hashCode(), differentIdModelEnsembleTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelEnsembleTypeDTO{"
            + "id='"
            + modelEnsembleTypeTO.getId()
            + "', name='"
            + modelEnsembleTypeTO.getName()
            + "'"
            + "}";
    assertEquals(expectedToString, modelEnsembleTypeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestModelEnsembleType", modelEnsembleTypeTO.getName());

    modelEnsembleTypeTO.setName("UpdatedTestModelEnsembleType");

    assertEquals("UpdatedTestModelEnsembleType", modelEnsembleTypeTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelEnsembleTypeTO.equals(differentObject));
  }
}
