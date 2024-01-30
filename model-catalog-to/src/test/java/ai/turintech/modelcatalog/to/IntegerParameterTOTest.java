package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerParameterTOTest {

  private IntegerParameterTO integerParameterTO;

  @BeforeEach
  public void setUp() {
    integerParameterTO = new IntegerParameterTO();
    integerParameterTO.setId(UUID.randomUUID());
    integerParameterTO.setDefaultValue(42);

    List<IntegerParameterValueTO> integerParameterValues = new ArrayList<>();
    IntegerParameterValueTO value1 = new IntegerParameterValueTO();
    value1.setId(UUID.randomUUID());
    value1.setLower(30);
    value1.setUpper(50);

    IntegerParameterValueTO value2 = new IntegerParameterValueTO();
    value2.setId(UUID.randomUUID());
    value2.setLower(10);
    value2.setUpper(20);

    integerParameterValues.add(value1);
    integerParameterValues.add(value2);

    integerParameterTO.setIntegerParameterValues(integerParameterValues);
  }

  @Test
  public void testEquals() {
    IntegerParameterTO sameIdIntegerParameterTO = new IntegerParameterTO();
    sameIdIntegerParameterTO.setId(integerParameterTO.getId());

    IntegerParameterTO differentIdIntegerParameterTO = new IntegerParameterTO();
    differentIdIntegerParameterTO.setId(UUID.randomUUID());

    assertEquals(integerParameterTO, sameIdIntegerParameterTO);
    assertNotEquals(integerParameterTO, differentIdIntegerParameterTO);
    assertNotEquals(integerParameterTO, null);
  }

  @Test
  public void testHashCode() {
    IntegerParameterTO sameIdIntegerParameterTO = new IntegerParameterTO();
    sameIdIntegerParameterTO.setId(integerParameterTO.getId());

    IntegerParameterTO differentIdIntegerParameterTO = new IntegerParameterTO();
    differentIdIntegerParameterTO.setId(UUID.randomUUID());

    assertEquals(integerParameterTO.hashCode(), sameIdIntegerParameterTO.hashCode());
    assertNotEquals(integerParameterTO.hashCode(), differentIdIntegerParameterTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "IntegerParameterDTO{"
            + "parameterTypeDefinitionId="
            + integerParameterTO.getId()
            + ", defaultValue="
            + integerParameterTO.getDefaultValue()
            + "}";
    assertEquals(expectedToString, integerParameterTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(42, integerParameterTO.getDefaultValue());

    integerParameterTO.setDefaultValue(100);

    assertEquals(100, integerParameterTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(integerParameterTO.equals(differentObject));
  }
}
