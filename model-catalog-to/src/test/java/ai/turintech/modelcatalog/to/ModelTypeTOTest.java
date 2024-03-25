package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTypeTOTest {

  private ModelTypeTO modelTypeTO;

  @BeforeEach
  public void setUp() {
    modelTypeTO = new ModelTypeTO();
    modelTypeTO.setId(UUID.randomUUID());
    modelTypeTO.setName("TestModelType");
  }

  @Test
  public void testEquals() {
    ModelTypeTO sameIdModelTypeTO = new ModelTypeTO();
    sameIdModelTypeTO.setId(modelTypeTO.getId());
    sameIdModelTypeTO.setName("TestModelType");

    ModelTypeTO differentIdModelTypeTO = new ModelTypeTO();
    differentIdModelTypeTO.setId(UUID.randomUUID());
    differentIdModelTypeTO.setName("DifferentTestModelType");

    assertEquals(modelTypeTO, sameIdModelTypeTO);
    assertNotEquals(modelTypeTO, differentIdModelTypeTO);
    assertNotEquals(modelTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ModelTypeTO sameIdModelTypeTO = new ModelTypeTO();
    sameIdModelTypeTO.setId(modelTypeTO.getId());
    sameIdModelTypeTO.setName("TestModelType");

    ModelTypeTO differentIdModelTypeTO = new ModelTypeTO();
    differentIdModelTypeTO.setId(UUID.randomUUID());
    differentIdModelTypeTO.setName("DifferentTestModelType");

    assertEquals(modelTypeTO.hashCode(), sameIdModelTypeTO.hashCode());
    assertNotEquals(modelTypeTO.hashCode(), differentIdModelTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelTypeDTO{" + "id='" + modelTypeTO.getId() + "', name='TestModelType'" + '}';
    assertEquals(expectedToString, modelTypeTO.toString());
  }

  @Test
  public void testAccessorMethods() {
    assertEquals(modelTypeTO.getId(), modelTypeTO.getId());
    assertEquals("TestModelType", modelTypeTO.getName());
  }

  @Test
  public void testNullId() {
    ModelTypeTO nullIdModelTypeTO = new ModelTypeTO();
    nullIdModelTypeTO.setId(null);
    assertNotEquals(modelTypeTO, nullIdModelTypeTO);
  }
}
