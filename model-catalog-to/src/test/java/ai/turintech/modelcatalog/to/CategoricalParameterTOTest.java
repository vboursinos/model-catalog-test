package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoricalParameterTOTest {

  private CategoricalParameterTO categoricalParameterTO;

  @BeforeEach
  public void setUp() {
    categoricalParameterTO = new CategoricalParameterTO();
    categoricalParameterTO.setId(UUID.randomUUID());
    categoricalParameterTO.setDefaultValue("Default");
    Set<CategoricalParameterValueTO> parameterValues = new HashSet<>();
    CategoricalParameterValueTO categoricalParameterValueTO = new CategoricalParameterValueTO();
    categoricalParameterValueTO.setValue("Category1");

    CategoricalParameterValueTO categoricalParameterValueTO2 = new CategoricalParameterValueTO();
    categoricalParameterValueTO2.setValue("Category2");

    parameterValues.add(categoricalParameterValueTO);
    parameterValues.add(categoricalParameterValueTO2);
    categoricalParameterTO.setCategoricalParameterValues(parameterValues);
  }

  @Test
  public void testEquals() {
    CategoricalParameterTO sameIdCategoricalParameterTO = new CategoricalParameterTO();
    sameIdCategoricalParameterTO.setId(categoricalParameterTO.getId());

    CategoricalParameterTO differentIdCategoricalParameterTO = new CategoricalParameterTO();
    differentIdCategoricalParameterTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterTO, sameIdCategoricalParameterTO);
    assertNotEquals(categoricalParameterTO, differentIdCategoricalParameterTO);
    assertNotEquals(categoricalParameterTO, null);
  }

  @Test
  public void testHashCode() {
    CategoricalParameterTO sameIdCategoricalParameterTO = new CategoricalParameterTO();
    sameIdCategoricalParameterTO.setId(categoricalParameterTO.getId());

    CategoricalParameterTO differentIdCategoricalParameterTO = new CategoricalParameterTO();
    differentIdCategoricalParameterTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterTO.hashCode(), sameIdCategoricalParameterTO.hashCode());
    assertNotEquals(
        categoricalParameterTO.hashCode(), differentIdCategoricalParameterTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "CategoricalParameterDTO{"
            + "parameterTypeDefinitionId="
            + categoricalParameterTO.getId()
            + ", defaultValue='"
            + categoricalParameterTO.getDefaultValue()
            + '\''
            + '}';
    assertEquals(expectedToString, categoricalParameterTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("Default", categoricalParameterTO.getDefaultValue());

    categoricalParameterTO.setDefaultValue("NewDefault");

    assertEquals("NewDefault", categoricalParameterTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(categoricalParameterTO.equals(differentObject));
  }
}
