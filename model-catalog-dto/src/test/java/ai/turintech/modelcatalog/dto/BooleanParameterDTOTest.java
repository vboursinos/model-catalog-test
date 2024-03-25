package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooleanParameterDTOTest {

  private BooleanParameterDTO booleanParameterDTO;

  @BeforeEach
  public void setUp() {
    booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.randomUUID());
    booleanParameterDTO.setDefaultValue(true);
  }

  @Test
  public void testEquals() {
    BooleanParameterDTO sameIdBooleanParameterDTO = new BooleanParameterDTO();
    sameIdBooleanParameterDTO.setId(booleanParameterDTO.getId());

    BooleanParameterDTO differentIdBooleanParameterDTO = new BooleanParameterDTO();
    differentIdBooleanParameterDTO.setId(UUID.randomUUID());

    assertEquals(booleanParameterDTO, sameIdBooleanParameterDTO);
    assertNotEquals(booleanParameterDTO, differentIdBooleanParameterDTO);
    assertNotEquals(booleanParameterDTO, null);
  }

  @Test
  public void testHashCode() {
    BooleanParameterDTO sameIdBooleanParameterDTO = new BooleanParameterDTO();
    sameIdBooleanParameterDTO.setId(booleanParameterDTO.getId());

    BooleanParameterDTO differentIdBooleanParameterDTO = new BooleanParameterDTO();
    differentIdBooleanParameterDTO.setId(UUID.randomUUID());

    assertEquals(booleanParameterDTO.hashCode(), sameIdBooleanParameterDTO.hashCode());
    assertNotEquals(booleanParameterDTO.hashCode(), differentIdBooleanParameterDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "BooleanParameterDTO{parameterTypeDefinitionId="
            + booleanParameterDTO.getId()
            + ", defaultValue=true}";
    assertEquals(expectedToString, booleanParameterDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(true, booleanParameterDTO.getDefaultValue());

    booleanParameterDTO.setDefaultValue(false);

    assertEquals(false, booleanParameterDTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(booleanParameterDTO.equals(differentObject));
  }
}
