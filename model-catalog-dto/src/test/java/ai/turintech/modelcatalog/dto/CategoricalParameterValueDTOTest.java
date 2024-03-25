package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoricalParameterValueDTOTest {

  private CategoricalParameterValueDTO categoricalParameterValueDTO;

  @BeforeAll
  public void setUp() {
    categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setId(UUID.randomUUID());
    categoricalParameterValueDTO.setValue("Category1");
  }

  @Test
  public void testEquals() {
    CategoricalParameterValueDTO sameIdCategoricalParameterValueDTO =
        new CategoricalParameterValueDTO();
    sameIdCategoricalParameterValueDTO.setId(categoricalParameterValueDTO.getId());

    CategoricalParameterValueDTO differentIdCategoricalParameterValueDTO =
        new CategoricalParameterValueDTO();
    differentIdCategoricalParameterValueDTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterValueDTO, sameIdCategoricalParameterValueDTO);
    assertNotEquals(categoricalParameterValueDTO, differentIdCategoricalParameterValueDTO);
    assertNotEquals(categoricalParameterValueDTO, null);
  }

  @Test
  public void testHashCode() {
    CategoricalParameterValueDTO sameIdCategoricalParameterValueDTO =
        new CategoricalParameterValueDTO();
    sameIdCategoricalParameterValueDTO.setId(categoricalParameterValueDTO.getId());

    CategoricalParameterValueDTO differentIdCategoricalParameterValueDTO =
        new CategoricalParameterValueDTO();
    differentIdCategoricalParameterValueDTO.setId(UUID.randomUUID());

    assertEquals(
        categoricalParameterValueDTO.hashCode(), sameIdCategoricalParameterValueDTO.hashCode());
    assertNotEquals(
        categoricalParameterValueDTO.hashCode(),
        differentIdCategoricalParameterValueDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "CategoricalParameterValueDTO{id="
            + categoricalParameterValueDTO.getId()
            + ", value='Category1'}";
    assertEquals(expectedToString, categoricalParameterValueDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("Category1", categoricalParameterValueDTO.getValue());

    categoricalParameterValueDTO.setValue("Category2");

    assertEquals("Category2", categoricalParameterValueDTO.getValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(categoricalParameterValueDTO.equals(differentObject));
  }
}
