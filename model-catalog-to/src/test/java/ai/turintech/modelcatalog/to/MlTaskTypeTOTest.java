package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MlTaskTypeTOTest {

  private MlTaskTypeTO mlTaskTypeTO;

  @BeforeEach
  public void setUp() {
    mlTaskTypeTO = new MlTaskTypeTO();
    mlTaskTypeTO.setId(UUID.randomUUID());
    mlTaskTypeTO.setName("TestMlTaskType");
  }

  @Test
  public void testEquals() {
    MlTaskTypeTO sameIdMlTaskTypeTO = new MlTaskTypeTO();
    sameIdMlTaskTypeTO.setId(mlTaskTypeTO.getId());
    sameIdMlTaskTypeTO.setName("TestMlTaskType");

    MlTaskTypeTO differentIdMlTaskTypeTO = new MlTaskTypeTO();
    differentIdMlTaskTypeTO.setId(UUID.randomUUID());
    differentIdMlTaskTypeTO.setName("DifferentTestMlTaskType");

    assertEquals(mlTaskTypeTO, sameIdMlTaskTypeTO);
    assertNotEquals(mlTaskTypeTO, differentIdMlTaskTypeTO);
    assertNotEquals(mlTaskTypeTO, null);
  }

  @Test
  public void testHashCode() {
    MlTaskTypeTO sameIdMlTaskTypeTO = new MlTaskTypeTO();
    sameIdMlTaskTypeTO.setId(mlTaskTypeTO.getId());
    sameIdMlTaskTypeTO.setName("TestMlTaskType");

    MlTaskTypeTO differentIdMlTaskTypeTO = new MlTaskTypeTO();
    differentIdMlTaskTypeTO.setId(UUID.randomUUID());
    differentIdMlTaskTypeTO.setName("DifferentTestMlTaskType");

    assertEquals(mlTaskTypeTO.hashCode(), sameIdMlTaskTypeTO.hashCode());
    assertNotEquals(mlTaskTypeTO.hashCode(), differentIdMlTaskTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "MlTaskTypeDTO{"
            + "id='"
            + mlTaskTypeTO.getId()
            + "', name='"
            + mlTaskTypeTO.getName()
            + "'"
            + "}";
    assertEquals(expectedToString, mlTaskTypeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestMlTaskType", mlTaskTypeTO.getName());

    mlTaskTypeTO.setName("UpdatedTestMlTaskType");

    assertEquals("UpdatedTestMlTaskType", mlTaskTypeTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(mlTaskTypeTO.equals(differentObject));
  }
}
