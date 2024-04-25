package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegerParameterDTOTest {

  private IntegerParameterDTO integerParameterDTO;

  @BeforeAll
  public void setUp() {
    integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setId(UUID.randomUUID());
    integerParameterDTO.setDefaultValue(42);

    // Create some integer parameter values for testing
    Set<IntegerParameterValueDTO> integerParameterValues = new HashSet<>();
    IntegerParameterValueDTO value1 = new IntegerParameterValueDTO();
    value1.setId(UUID.randomUUID());
    value1.setLower(10);
    value1.setUpper(20);
    integerParameterValues.add(value1);

    IntegerParameterValueDTO value2 = new IntegerParameterValueDTO();
    value2.setId(UUID.randomUUID());
    value2.setLower(20);
    value2.setUpper(30);
    integerParameterValues.add(value2);

    integerParameterDTO.setIntegerParameterValues(integerParameterValues);
  }

  @Test
  public void testEquals() {
    IntegerParameterDTO sameIdIntegerParameterDTO = new IntegerParameterDTO();
    sameIdIntegerParameterDTO.setId(integerParameterDTO.getId());

    IntegerParameterDTO differentIdIntegerParameterDTO = new IntegerParameterDTO();
    differentIdIntegerParameterDTO.setId(UUID.randomUUID());

    assertEquals(integerParameterDTO, sameIdIntegerParameterDTO);
    assertNotEquals(integerParameterDTO, differentIdIntegerParameterDTO);
    assertNotEquals(integerParameterDTO, null);
  }

  @Test
  public void testHashCode() {
    IntegerParameterDTO sameIdIntegerParameterDTO = new IntegerParameterDTO();
    sameIdIntegerParameterDTO.setId(integerParameterDTO.getId());

    IntegerParameterDTO differentIdIntegerParameterDTO = new IntegerParameterDTO();
    differentIdIntegerParameterDTO.setId(UUID.randomUUID());

    assertEquals(integerParameterDTO.hashCode(), sameIdIntegerParameterDTO.hashCode());
    assertNotEquals(integerParameterDTO.hashCode(), differentIdIntegerParameterDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "IntegerParameterDTO{parameterTypeDefinitionId="
            + integerParameterDTO.getId()
            + ", defaultValue="
            + integerParameterDTO.getDefaultValue()
            + "}";
    assertEquals(expectedToString, integerParameterDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(42, integerParameterDTO.getDefaultValue());

    integerParameterDTO.setDefaultValue(100);

    assertEquals(100, integerParameterDTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(integerParameterDTO.equals(differentObject));
  }
}
