package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoricalParameterValueTOTest {

  private CategoricalParameterValueTO categoricalParameterValueTO;

  @BeforeEach
  public void setUp() {
    categoricalParameterValueTO = new CategoricalParameterValueTO();
    categoricalParameterValueTO.setId(UUID.randomUUID());
    categoricalParameterValueTO.setValue("Value1");
  }

  @Test
  public void testEquals() {
    CategoricalParameterValueTO sameIdCategoricalParameterValueTO =
        new CategoricalParameterValueTO();
    sameIdCategoricalParameterValueTO.setId(categoricalParameterValueTO.getId());

    CategoricalParameterValueTO differentIdCategoricalParameterValueTO =
        new CategoricalParameterValueTO();
    differentIdCategoricalParameterValueTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterValueTO, sameIdCategoricalParameterValueTO);
    assertNotEquals(categoricalParameterValueTO, differentIdCategoricalParameterValueTO);
    assertNotEquals(categoricalParameterValueTO, null);
  }

  @Test
  public void testHashCode() {
    CategoricalParameterValueTO sameIdCategoricalParameterValueTO =
        new CategoricalParameterValueTO();
    sameIdCategoricalParameterValueTO.setId(categoricalParameterValueTO.getId());

    CategoricalParameterValueTO differentIdCategoricalParameterValueTO =
        new CategoricalParameterValueTO();
    differentIdCategoricalParameterValueTO.setId(UUID.randomUUID());

    assertEquals(
        categoricalParameterValueTO.hashCode(), sameIdCategoricalParameterValueTO.hashCode());
    assertNotEquals(
        categoricalParameterValueTO.hashCode(), differentIdCategoricalParameterValueTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "CategoricalParameterValueDTO{"
            + "id="
            + categoricalParameterValueTO.getId()
            + ", value='"
            + categoricalParameterValueTO.getValue()
            + "'"
            + "}";
    assertEquals(expectedToString, categoricalParameterValueTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("Value1", categoricalParameterValueTO.getValue());

    categoricalParameterValueTO.setValue("NewValue");

    assertEquals("NewValue", categoricalParameterValueTO.getValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(categoricalParameterValueTO.equals(differentObject));
  }
}
