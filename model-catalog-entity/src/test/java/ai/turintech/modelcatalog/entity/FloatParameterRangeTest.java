package ai.turintech.modelcatalog.entity;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FloatParameterRangeTest {
  private final FloatParameterRange floatParameterRange = new FloatParameterRange();

  @Test
  public void testIsLeftOpen() {
    Boolean isLeftOpen = true;
    floatParameterRange.setIsLeftOpen(isLeftOpen);
    Assertions.assertEquals(isLeftOpen, floatParameterRange.getIsLeftOpen());
  }

  @Test
  public void testIsRightOpen() {
    Boolean isRightOpen = true;
    floatParameterRange.setIsRightOpen(isRightOpen);
    Assertions.assertEquals(isRightOpen, floatParameterRange.getIsRightOpen());
  }

  @Test
  public void testLower() {
    Double lower = 1.0;
    floatParameterRange.setLower(lower);
    Assertions.assertEquals(lower, floatParameterRange.getLower());
  }

  @Test
  public void testUpper() {
    Double upper = 1.0;
    floatParameterRange.setUpper(upper);
    Assertions.assertEquals(upper, floatParameterRange.getUpper());
  }

  @Test
  public void testFloatParameter() {
    FloatParameter floatParameter = new FloatParameter();
    floatParameterRange.setFloatParameter(floatParameter);
    Assertions.assertEquals(floatParameter, floatParameterRange.getFloatParameter());
  }

  @Test
  public void testEqualsAndHashcode() {
    Assertions.assertDoesNotThrow(
        () -> {
          FloatParameterRange floatParameterRange1 = new FloatParameterRange();
          FloatParameterRange floatParameterRange2 = new FloatParameterRange();
          UUID id = UUID.randomUUID();
          floatParameterRange1.setId(id);
          floatParameterRange2.setId(id);

          Assertions.assertEquals(floatParameterRange1, floatParameterRange2);
          Assertions.assertEquals(floatParameterRange1.hashCode(), floatParameterRange2.hashCode());
        });
  }

  @Test
  public void testToString() {
    UUID id = UUID.randomUUID();
    floatParameterRange.setId(id);
    floatParameterRange.setIsLeftOpen(true);
    floatParameterRange.setIsRightOpen(true);
    floatParameterRange.setLower(1.0);
    floatParameterRange.setUpper(2.0);

    String expectedStringValue =
        "FloatParameterRange{id="
            + id
            + ", isLeftOpen='true', isRightOpen='true', lower=1.0, upper=2.0}";
    System.out.println(floatParameterRange.toString());
    Assertions.assertEquals(expectedStringValue, floatParameterRange.toString());
  }
}
