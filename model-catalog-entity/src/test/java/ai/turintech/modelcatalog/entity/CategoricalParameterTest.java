package ai.turintech.modelcatalog.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoricalParameterTest {

  private final CategoricalParameter categoricalParameter = new CategoricalParameter();

  @Test
  public void testDefaultValue() {
    String defaultValue = "value";
    categoricalParameter.setDefaultValue(defaultValue);
    Assertions.assertEquals(defaultValue, categoricalParameter.getDefaultValue());
  }

  @Test
  public void testParameterTypeDefinition() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);
    Assertions.assertEquals(
        parameterTypeDefinition, categoricalParameter.getParameterTypeDefinition());
  }

  @Test
  public void testCategoricalParameterValues() {
    Set<CategoricalParameterValue> categoricalParameterValues = new HashSet<>();
    CategoricalParameterValue categoricalParameterValue = new CategoricalParameterValue();
    categoricalParameterValues.add(categoricalParameterValue);

    categoricalParameter.setCategoricalParameterValues(categoricalParameterValues);
    Assertions.assertEquals(
        categoricalParameterValues, categoricalParameter.getCategoricalParameterValues());
  }

  @Test
  public void testToString() {
    UUID id = UUID.randomUUID();
    String expectedStringValue =
        "CategoricalParameter{defaultValue='null', parameterTypeDefinition=ParameterTypeDefinition{id="
            + id
            + ", ordering=1}}";
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(id);
    parameterTypeDefinition.setOrdering(1);
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);
    Assertions.assertEquals(expectedStringValue, categoricalParameter.toString());
  }
}
