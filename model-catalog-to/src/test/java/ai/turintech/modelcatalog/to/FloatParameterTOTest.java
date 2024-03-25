package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FloatParameterTOTest {

  private FloatParameterTO floatParameterTO;

  @BeforeEach
  public void setUp() {
    floatParameterTO = new FloatParameterTO();
    floatParameterTO.setId(UUID.randomUUID());
    floatParameterTO.setDefaultValue(15.0);

    List<FloatParameterRangeTO> floatParameterRanges = new ArrayList<>();
    FloatParameterRangeTO range1 = new FloatParameterRangeTO();
    range1.setId(UUID.randomUUID());
    range1.setIsLeftOpen(true);
    range1.setIsRightOpen(false);
    range1.setLower(10.0);
    range1.setUpper(20.0);

    FloatParameterRangeTO range2 = new FloatParameterRangeTO();
    range2.setId(UUID.randomUUID());
    range2.setIsLeftOpen(false);
    range2.setIsRightOpen(true);
    range2.setLower(5.0);
    range2.setUpper(15.0);

    floatParameterRanges.add(range1);
    floatParameterRanges.add(range2);

    floatParameterTO.setFloatParameterRanges(floatParameterRanges);
  }

  @Test
  public void testEquals() {
    FloatParameterTO sameIdFloatParameterTO = new FloatParameterTO();
    sameIdFloatParameterTO.setId(floatParameterTO.getId());

    FloatParameterTO differentIdFloatParameterTO = new FloatParameterTO();
    differentIdFloatParameterTO.setId(UUID.randomUUID());

    assertEquals(floatParameterTO, sameIdFloatParameterTO);
    assertNotEquals(floatParameterTO, differentIdFloatParameterTO);
    assertNotEquals(floatParameterTO, null);
  }

  @Test
  public void testHashCode() {
    FloatParameterTO sameIdFloatParameterTO = new FloatParameterTO();
    sameIdFloatParameterTO.setId(floatParameterTO.getId());

    FloatParameterTO differentIdFloatParameterTO = new FloatParameterTO();
    differentIdFloatParameterTO.setId(UUID.randomUUID());

    assertEquals(floatParameterTO.hashCode(), sameIdFloatParameterTO.hashCode());
    assertNotEquals(floatParameterTO.hashCode(), differentIdFloatParameterTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "FloatParameterDTO{"
            + "parameterTypeDefinitionId="
            + floatParameterTO.getId()
            + ", defaultValue="
            + floatParameterTO.getDefaultValue()
            + "}";
    assertEquals(expectedToString, floatParameterTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(15.0, floatParameterTO.getDefaultValue());

    floatParameterTO.setDefaultValue(20.0);

    assertEquals(20.0, floatParameterTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(floatParameterTO.equals(differentObject));
  }
}
