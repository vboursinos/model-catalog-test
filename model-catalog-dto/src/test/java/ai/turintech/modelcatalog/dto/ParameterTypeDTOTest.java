package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterTypeDTOTest {

  private ParameterTypeDTO parameterTypeDTO;

  @BeforeEach
  public void setUp() {
    parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.randomUUID());
    parameterTypeDTO.setName("TestParameterType");
  }

  @Test
  public void testEquals() {
    ParameterTypeDTO sameIdParameterTypeDTO = new ParameterTypeDTO();
    sameIdParameterTypeDTO.setId(parameterTypeDTO.getId());

    ParameterTypeDTO differentIdParameterTypeDTO = new ParameterTypeDTO();
    differentIdParameterTypeDTO.setId(UUID.randomUUID());

    assertEquals(parameterTypeDTO, sameIdParameterTypeDTO);
    assertNotEquals(parameterTypeDTO, differentIdParameterTypeDTO);
    assertNotEquals(parameterTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterTypeDTO sameIdParameterTypeDTO = new ParameterTypeDTO();
    sameIdParameterTypeDTO.setId(parameterTypeDTO.getId());

    ParameterTypeDTO differentIdParameterTypeDTO = new ParameterTypeDTO();
    differentIdParameterTypeDTO.setId(UUID.randomUUID());

    assertEquals(parameterTypeDTO.hashCode(), sameIdParameterTypeDTO.hashCode());
    assertNotEquals(parameterTypeDTO.hashCode(), differentIdParameterTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterTypeDTO{id='" + parameterTypeDTO.getId() + "', name='TestParameterType'}";
    assertEquals(expectedToString, parameterTypeDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestParameterType", parameterTypeDTO.getName());

    parameterTypeDTO.setName("UpdatedTestParameterType");

    assertEquals("UpdatedTestParameterType", parameterTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(parameterTypeDTO.equals(differentObject));
  }
}
