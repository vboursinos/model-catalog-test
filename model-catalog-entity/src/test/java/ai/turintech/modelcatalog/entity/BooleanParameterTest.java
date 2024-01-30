package ai.turintech.modelcatalog.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooleanParameterTest {

  private BooleanParameter booleanParameter;

  @BeforeEach
  public void setUp() {
    booleanParameter = new BooleanParameter();
  }

  @Test
  public void testDefaultValue() {
    Boolean defaultValue = true;
    booleanParameter.setDefaultValue(defaultValue);
    Assertions.assertEquals(defaultValue, booleanParameter.getDefaultValue());
  }

  @Test
  public void testParameterTypeDefinition() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    booleanParameter.setParameterTypeDefinition(parameterTypeDefinition);
    Assertions.assertEquals(parameterTypeDefinition, booleanParameter.getParameterTypeDefinition());
  }

  @Test
  public void testToString() {
    // assuming defaultValue initially set to null
    String expectedStringValue =
        "BooleanParameter{defaultValue=null, parameterTypeDefinition=null}";
    Assertions.assertEquals(expectedStringValue, booleanParameter.toString());
  }
}
