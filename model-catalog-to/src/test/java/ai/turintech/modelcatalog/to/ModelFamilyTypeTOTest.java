package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelFamilyTypeTOTest {

  private ModelFamilyTypeTO modelFamilyTypeTO;

  @BeforeEach
  public void setUp() {
    modelFamilyTypeTO = new ModelFamilyTypeTO();
    modelFamilyTypeTO.setId(UUID.randomUUID());
    modelFamilyTypeTO.setName("TestModelFamilyType");
  }

  @Test
  public void testEquals() {
    ModelFamilyTypeTO sameIdModelFamilyTypeTO = new ModelFamilyTypeTO();
    sameIdModelFamilyTypeTO.setId(modelFamilyTypeTO.getId());
    sameIdModelFamilyTypeTO.setName("TestModelFamilyType");

    ModelFamilyTypeTO differentIdModelFamilyTypeTO = new ModelFamilyTypeTO();
    differentIdModelFamilyTypeTO.setId(UUID.randomUUID());
    differentIdModelFamilyTypeTO.setName("DifferentTestModelFamilyType");

    assertEquals(modelFamilyTypeTO, sameIdModelFamilyTypeTO);
    assertNotEquals(modelFamilyTypeTO, differentIdModelFamilyTypeTO);
    assertNotEquals(modelFamilyTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ModelFamilyTypeTO sameIdModelFamilyTypeTO = new ModelFamilyTypeTO();
    sameIdModelFamilyTypeTO.setId(modelFamilyTypeTO.getId());
    sameIdModelFamilyTypeTO.setName("TestModelFamilyType");

    ModelFamilyTypeTO differentIdModelFamilyTypeTO = new ModelFamilyTypeTO();
    differentIdModelFamilyTypeTO.setId(UUID.randomUUID());
    differentIdModelFamilyTypeTO.setName("DifferentTestModelFamilyType");

    assertEquals(modelFamilyTypeTO.hashCode(), sameIdModelFamilyTypeTO.hashCode());
    assertNotEquals(modelFamilyTypeTO.hashCode(), differentIdModelFamilyTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelFamilyTypeDTO{"
            + "id='"
            + modelFamilyTypeTO.getId()
            + "', name='"
            + modelFamilyTypeTO.getName()
            + "'"
            + "}";
    assertEquals(expectedToString, modelFamilyTypeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestModelFamilyType", modelFamilyTypeTO.getName());

    modelFamilyTypeTO.setName("UpdatedTestModelFamilyType");

    assertEquals("UpdatedTestModelFamilyType", modelFamilyTypeTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelFamilyTypeTO.equals(differentObject));
  }
}
