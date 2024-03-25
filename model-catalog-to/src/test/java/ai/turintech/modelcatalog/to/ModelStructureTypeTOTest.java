package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelStructureTypeTOTest {

  private ModelStructureTypeTO modelStructureTypeTO;

  @BeforeEach
  public void setUp() {
    modelStructureTypeTO = new ModelStructureTypeTO();
    modelStructureTypeTO.setId(UUID.randomUUID());
    modelStructureTypeTO.setName("TestModelStructureType");
  }

  @Test
  public void testEquals() {
    ModelStructureTypeTO sameIdModelStructureTypeTO = new ModelStructureTypeTO();
    sameIdModelStructureTypeTO.setId(modelStructureTypeTO.getId());
    sameIdModelStructureTypeTO.setName("TestModelStructureType");

    ModelStructureTypeTO differentIdModelStructureTypeTO = new ModelStructureTypeTO();
    differentIdModelStructureTypeTO.setId(UUID.randomUUID());
    differentIdModelStructureTypeTO.setName("DifferentTestModelStructureType");

    assertEquals(modelStructureTypeTO, sameIdModelStructureTypeTO);
    assertNotEquals(modelStructureTypeTO, differentIdModelStructureTypeTO);
    assertNotEquals(modelStructureTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ModelStructureTypeTO sameIdModelStructureTypeTO = new ModelStructureTypeTO();
    sameIdModelStructureTypeTO.setId(modelStructureTypeTO.getId());
    sameIdModelStructureTypeTO.setName("TestModelStructureType");

    ModelStructureTypeTO differentIdModelStructureTypeTO = new ModelStructureTypeTO();
    differentIdModelStructureTypeTO.setId(UUID.randomUUID());
    differentIdModelStructureTypeTO.setName("DifferentTestModelStructureType");

    assertEquals(modelStructureTypeTO.hashCode(), sameIdModelStructureTypeTO.hashCode());
    assertNotEquals(modelStructureTypeTO.hashCode(), differentIdModelStructureTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelStructureTypeDTO{"
            + "id='"
            + modelStructureTypeTO.getId()
            + "', name='"
            + modelStructureTypeTO.getName()
            + "'"
            + "}";
    assertEquals(expectedToString, modelStructureTypeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestModelStructureType", modelStructureTypeTO.getName());

    modelStructureTypeTO.setName("UpdatedTestModelStructureType");

    assertEquals("UpdatedTestModelStructureType", modelStructureTypeTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelStructureTypeTO.equals(differentObject));
  }
}
