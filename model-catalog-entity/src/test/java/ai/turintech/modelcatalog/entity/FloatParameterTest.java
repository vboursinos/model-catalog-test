package ai.turintech.modelcatalog.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FloatParameterTest {
  private final FloatParameter floatParameter = new FloatParameter();

  @Test
  public void testDefaultValue() {
    Double defaultValue = 1.0;
    floatParameter.setDefaultValue(defaultValue);
    Assertions.assertEquals(defaultValue, floatParameter.getDefaultValue());
  }

  @Test
  public void testFloatParameterRanges() {
    Set<FloatParameterRange> floatParameterRanges = new HashSet<>();
    FloatParameterRange floatParameterRange = new FloatParameterRange();
    floatParameterRanges.add(floatParameterRange);
    floatParameter.setFloatParameterRanges(floatParameterRanges);
    Assertions.assertEquals(floatParameterRanges, floatParameter.getFloatParameterRanges());
  }

  @Test
  public void testToString() {
    UUID id = UUID.randomUUID();
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(id);
    parameterTypeDefinition.setOrdering(1);
    floatParameter.setDefaultValue(0.1);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);
    String expectedStringValue =
        "FloatParameter{defaultValue=0.1, parameterTypeDefinition=ParameterTypeDefinition{id="
            + id
            + ", ordering=1}}";
    Assertions.assertEquals(expectedStringValue, floatParameter.toString());
  }
}
