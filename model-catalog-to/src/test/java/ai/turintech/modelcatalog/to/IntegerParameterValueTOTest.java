package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerParameterValueTOTest {

  private IntegerParameterValueTO integerParameterValueTO;

  @BeforeEach
  public void setUp() {
    integerParameterValueTO = new IntegerParameterValueTO();
    integerParameterValueTO.setId(UUID.randomUUID());
    integerParameterValueTO.setLower(10);
    integerParameterValueTO.setUpper(20);
  }

  @Test
  public void testToString() {
    String expectedToString =
        "IntegerParameterValueDTO{"
            + "id="
            + integerParameterValueTO.getId()
            + ", lower="
            + integerParameterValueTO.getLower()
            + ", upper="
            + integerParameterValueTO.getUpper()
            + "}";
    assertEquals(expectedToString, integerParameterValueTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(10, integerParameterValueTO.getLower());
    assertEquals(20, integerParameterValueTO.getUpper());

    integerParameterValueTO.setLower(30);
    integerParameterValueTO.setUpper(40);

    assertEquals(30, integerParameterValueTO.getLower());
    assertEquals(40, integerParameterValueTO.getUpper());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(integerParameterValueTO.equals(differentObject));
  }
}
