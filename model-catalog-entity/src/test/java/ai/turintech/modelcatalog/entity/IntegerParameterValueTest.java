package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerParameterValueTest {

  private IntegerParameterValue integerParameterValueUnderTest;

  @BeforeEach
  public void setUp() {
    integerParameterValueUnderTest = new IntegerParameterValue();
  }

  @Test
  public void testUpperValue() {
    Integer expectedUpperValue = 10;
    integerParameterValueUnderTest.upper(expectedUpperValue);

    Integer actualUpperValue = integerParameterValueUnderTest.getUpper();

    assertNotNull(actualUpperValue, "Expected upper value to be not null");
    assertEquals(
        expectedUpperValue,
        actualUpperValue,
        "Expected and actual upper values should be the same");
  }

  @Test
  public void testLowerValue() {
    Integer expectedLowerValue = 5;
    integerParameterValueUnderTest.lower(expectedLowerValue);

    Integer actualLowerValue = integerParameterValueUnderTest.getLower();

    assertNotNull(actualLowerValue, "Expected lower value to be not null");
    assertEquals(
        expectedLowerValue,
        actualLowerValue,
        "Expected and actual lower values should be the same");
  }

  @Test
  public void testSetAndGetIntegerParameter() {
    IntegerParameter integerParameter = new IntegerParameter();
    integerParameterValueUnderTest.setIntegerParameter(integerParameter);

    IntegerParameter actualValue = integerParameterValueUnderTest.getIntegerParameter();

    assertSame(integerParameter, actualValue, "Expected same IntegerParameter");
  }

  @Test
  public void testToString() {
    Integer upperValue = 10;
    Integer lowerValue = 5;
    integerParameterValueUnderTest.upper(upperValue);
    integerParameterValueUnderTest.lower(lowerValue);

    String expectedString =
        "IntegerParameterValue{"
            + "id="
            + integerParameterValueUnderTest.getId()
            + ", lower="
            + lowerValue
            + ", upper="
            + upperValue
            + "}";

    assertEquals(
        expectedString,
        integerParameterValueUnderTest.toString(),
        "Expected and actual toString should be same");
  }
}
