package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooleanParameterTOTest {

  private BooleanParameterTO booleanParameterTO;

  @BeforeEach
  public void setUp() {
    booleanParameterTO = new BooleanParameterTO();
    booleanParameterTO.setId(UUID.randomUUID());
    booleanParameterTO.setDefaultValue(true);
  }

  @Test
  public void testEquals() {
    BooleanParameterTO sameIdBooleanParameterTO = new BooleanParameterTO();
    sameIdBooleanParameterTO.setId(booleanParameterTO.getId());

    BooleanParameterTO differentIdBooleanParameterTO = new BooleanParameterTO();
    differentIdBooleanParameterTO.setId(UUID.randomUUID());

    assertEquals(booleanParameterTO, sameIdBooleanParameterTO);
    assertNotEquals(booleanParameterTO, differentIdBooleanParameterTO);
    assertNotEquals(booleanParameterTO, null);
  }

  @Test
  public void testHashCode() {
    BooleanParameterTO sameIdBooleanParameterTO = new BooleanParameterTO();
    sameIdBooleanParameterTO.setId(booleanParameterTO.getId());

    BooleanParameterTO differentIdBooleanParameterTO = new BooleanParameterTO();
    differentIdBooleanParameterTO.setId(UUID.randomUUID());

    assertEquals(booleanParameterTO.hashCode(), sameIdBooleanParameterTO.hashCode());
    assertNotEquals(booleanParameterTO.hashCode(), differentIdBooleanParameterTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "BooleanParameterDTO{"
            + "parameterTypeDefinitionId="
            + booleanParameterTO.getId()
            + ", defaultValue="
            + booleanParameterTO.getDefaultValue()
            + '}';
    assertEquals(expectedToString, booleanParameterTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(true, booleanParameterTO.getDefaultValue());

    booleanParameterTO.setDefaultValue(false);

    assertEquals(false, booleanParameterTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(booleanParameterTO.equals(differentObject));
  }
}
