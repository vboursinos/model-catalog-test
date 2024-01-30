package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoricalParameterDTOTest {

  private CategoricalParameterDTO categoricalParameterDTO;

  @BeforeEach
  public void setUp() {
    categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.randomUUID());
    categoricalParameterDTO.setDefaultValue("Category1");

    Set<CategoricalParameterValueDTO> parameterValues = new HashSet<>();
    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setValue("Category1");

    CategoricalParameterValueDTO categoricalParameterValueDTO2 = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO2.setValue("Category2");

    parameterValues.add(categoricalParameterValueDTO);
    parameterValues.add(categoricalParameterValueDTO2);
    categoricalParameterDTO.setCategoricalParameterValues(parameterValues);
  }

  @Test
  public void testEquals() {
    CategoricalParameterDTO sameIdCategoricalParameterDTO = new CategoricalParameterDTO();
    sameIdCategoricalParameterDTO.setId(categoricalParameterDTO.getId());

    CategoricalParameterDTO differentIdCategoricalParameterDTO = new CategoricalParameterDTO();
    differentIdCategoricalParameterDTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterDTO, sameIdCategoricalParameterDTO);
    assertNotEquals(categoricalParameterDTO, differentIdCategoricalParameterDTO);
    assertNotEquals(categoricalParameterDTO, null);
  }

  @Test
  public void testHashCode() {
    CategoricalParameterDTO sameIdCategoricalParameterDTO = new CategoricalParameterDTO();
    sameIdCategoricalParameterDTO.setId(categoricalParameterDTO.getId());

    CategoricalParameterDTO differentIdCategoricalParameterDTO = new CategoricalParameterDTO();
    differentIdCategoricalParameterDTO.setId(UUID.randomUUID());

    assertEquals(categoricalParameterDTO.hashCode(), sameIdCategoricalParameterDTO.hashCode());
    assertNotEquals(
        categoricalParameterDTO.hashCode(), differentIdCategoricalParameterDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "CategoricalParameterDTO{parameterTypeDefinitionId="
            + categoricalParameterDTO.getId()
            + ", defaultValue='Category1'}";
    assertEquals(expectedToString, categoricalParameterDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("Category1", categoricalParameterDTO.getDefaultValue());

    Set<CategoricalParameterValueDTO> updatedParameterValues = new HashSet<>();
    CategoricalParameterValueDTO categoricalParameterValueDTO3 = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO3.setValue("Category3");

    CategoricalParameterValueDTO categoricalParameterValueDTO4 = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO4.setValue("Category4");
    updatedParameterValues.add(categoricalParameterValueDTO3);
    updatedParameterValues.add(categoricalParameterValueDTO4);

    categoricalParameterDTO.setDefaultValue("Category2");
    categoricalParameterDTO.setCategoricalParameterValues(updatedParameterValues);

    assertEquals("Category2", categoricalParameterDTO.getDefaultValue());
    assertEquals(updatedParameterValues, categoricalParameterDTO.getCategoricalParameterValues());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(categoricalParameterDTO.equals(differentObject));
  }
}
