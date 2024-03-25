package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelGroupTypeTOTest {

  private ModelGroupTypeTO modelGroupTypeTO;

  @BeforeEach
  public void setUp() {
    modelGroupTypeTO = new ModelGroupTypeTO();
    modelGroupTypeTO.setId(UUID.randomUUID());
    modelGroupTypeTO.setName("TestModelGroupType");
  }

  @Test
  public void testEquals() {
    ModelGroupTypeTO sameIdModelGroupTypeTO = new ModelGroupTypeTO();
    sameIdModelGroupTypeTO.setId(modelGroupTypeTO.getId());
    sameIdModelGroupTypeTO.setName("TestModelGroupType");

    ModelGroupTypeTO differentIdModelGroupTypeTO = new ModelGroupTypeTO();
    differentIdModelGroupTypeTO.setId(UUID.randomUUID());
    differentIdModelGroupTypeTO.setName("DifferentTestModelGroupType");

    assertEquals(modelGroupTypeTO, sameIdModelGroupTypeTO);
    assertNotEquals(modelGroupTypeTO, differentIdModelGroupTypeTO);
    assertNotEquals(modelGroupTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ModelGroupTypeTO sameIdModelGroupTypeTO = new ModelGroupTypeTO();
    sameIdModelGroupTypeTO.setId(modelGroupTypeTO.getId());
    sameIdModelGroupTypeTO.setName("TestModelGroupType");

    ModelGroupTypeTO differentIdModelGroupTypeTO = new ModelGroupTypeTO();
    differentIdModelGroupTypeTO.setId(UUID.randomUUID());
    differentIdModelGroupTypeTO.setName("DifferentTestModelGroupType");

    assertEquals(modelGroupTypeTO.hashCode(), sameIdModelGroupTypeTO.hashCode());
    assertNotEquals(modelGroupTypeTO.hashCode(), differentIdModelGroupTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelGroupTypeDTO{"
            + "id='"
            + modelGroupTypeTO.getId()
            + "', name='"
            + modelGroupTypeTO.getName()
            + "'"
            + "}";
    assertEquals(expectedToString, modelGroupTypeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestModelGroupType", modelGroupTypeTO.getName());

    modelGroupTypeTO.setName("UpdatedTestModelGroupType");

    assertEquals("UpdatedTestModelGroupType", modelGroupTypeTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelGroupTypeTO.equals(differentObject));
  }
}
