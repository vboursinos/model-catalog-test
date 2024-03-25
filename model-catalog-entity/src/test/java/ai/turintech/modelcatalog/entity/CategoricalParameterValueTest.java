package ai.turintech.modelcatalog.entity;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoricalParameterValueTest {

  private final CategoricalParameterValue categoricalParameterValue =
      new CategoricalParameterValue();

  @Test
  public void testValue() {
    String value = "value";
    categoricalParameterValue.setValue(value);
    Assertions.assertEquals(value, categoricalParameterValue.getValue());
  }

  @Test
  public void testCategoricalParameter() {
    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameterValue.setCategoricalParameter(categoricalParameter);
    Assertions.assertEquals(
        categoricalParameter, categoricalParameterValue.getCategoricalParameter());
  }

  @Test
  public void testEqualsAndHashcode() {
    Assertions.assertDoesNotThrow(
        () -> {
          CategoricalParameterValue categoricalParameterValue1 = new CategoricalParameterValue();
          CategoricalParameterValue categoricalParameterValue2 = new CategoricalParameterValue();
          UUID id = UUID.randomUUID();
          categoricalParameterValue1.setId(id);
          categoricalParameterValue2.setId(id);

          Assertions.assertEquals(categoricalParameterValue1, categoricalParameterValue2);
          Assertions.assertEquals(
              categoricalParameterValue1.hashCode(), categoricalParameterValue2.hashCode());
        });
  }

  @Test
  public void testToString() {
    UUID id = UUID.randomUUID();
    categoricalParameterValue.setId(id);
    categoricalParameterValue.setValue("value");
    String expectedStringValue = "CategoricalParameterValue{id=" + id + ", value='value'}";
    Assertions.assertEquals(expectedStringValue, categoricalParameterValue.toString());
  }
}
