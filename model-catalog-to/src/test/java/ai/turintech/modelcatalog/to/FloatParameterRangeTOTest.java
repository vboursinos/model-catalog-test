package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FloatParameterRangeTOTest {

  private FloatParameterRangeTO floatParameterRangeTO;

  @BeforeEach
  public void setUp() {
    floatParameterRangeTO = new FloatParameterRangeTO();
    floatParameterRangeTO.setId(UUID.randomUUID());
    floatParameterRangeTO.setIsLeftOpen(true);
    floatParameterRangeTO.setIsRightOpen(false);
    floatParameterRangeTO.setLower(10.0);
    floatParameterRangeTO.setUpper(20.0);
  }

  @Test
  public void testToString() {
    String expectedToString =
        "FloatParameterRangeDTO{"
            + "id="
            + floatParameterRangeTO.getId()
            + ", isLeftOpen='"
            + floatParameterRangeTO.getIsLeftOpen()
            + "'"
            + ", isRightOpen='"
            + floatParameterRangeTO.getIsRightOpen()
            + "'"
            + ", lower="
            + floatParameterRangeTO.getLower()
            + ", upper="
            + floatParameterRangeTO.getUpper()
            + "}";
    assertEquals(expectedToString, floatParameterRangeTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertTrue(floatParameterRangeTO.getIsLeftOpen());
    assertFalse(floatParameterRangeTO.getIsRightOpen());
    assertEquals(10.0, floatParameterRangeTO.getLower());
    assertEquals(20.0, floatParameterRangeTO.getUpper());

    floatParameterRangeTO.setIsLeftOpen(false);
    floatParameterRangeTO.setIsRightOpen(true);
    floatParameterRangeTO.setLower(5.0);
    floatParameterRangeTO.setUpper(15.0);

    assertFalse(floatParameterRangeTO.getIsLeftOpen());
    assertTrue(floatParameterRangeTO.getIsRightOpen());
    assertEquals(5.0, floatParameterRangeTO.getLower());
    assertEquals(15.0, floatParameterRangeTO.getUpper());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(floatParameterRangeTO.equals(differentObject));
  }
}
