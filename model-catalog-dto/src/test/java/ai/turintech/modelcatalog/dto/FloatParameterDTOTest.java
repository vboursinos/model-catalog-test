package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FloatParameterDTOTest {

  private FloatParameterDTO floatParameterDTO;

  @BeforeAll
  public void setUp() {
    floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.randomUUID());
    floatParameterDTO.setDefaultValue(10.5);
    List<FloatParameterRangeDTO> floatParameterRanges = new ArrayList<>();
    floatParameterRanges.add(new FloatParameterRangeDTO());
    floatParameterDTO.setFloatParameterRanges(floatParameterRanges);
  }

  @Test
  public void testEquals() {
    FloatParameterDTO sameIdFloatParameterDTO = new FloatParameterDTO();
    sameIdFloatParameterDTO.setId(floatParameterDTO.getId());

    FloatParameterDTO differentIdFloatParameterDTO = new FloatParameterDTO();
    differentIdFloatParameterDTO.setId(UUID.randomUUID());

    assertEquals(floatParameterDTO, sameIdFloatParameterDTO);
    assertNotEquals(floatParameterDTO, differentIdFloatParameterDTO);
    assertNotEquals(floatParameterDTO, null);
  }

  @Test
  public void testHashCode() {
    FloatParameterDTO sameIdFloatParameterDTO = new FloatParameterDTO();
    sameIdFloatParameterDTO.setId(floatParameterDTO.getId());

    FloatParameterDTO differentIdFloatParameterDTO = new FloatParameterDTO();
    differentIdFloatParameterDTO.setId(UUID.randomUUID());

    assertEquals(floatParameterDTO.hashCode(), sameIdFloatParameterDTO.hashCode());
    assertNotEquals(floatParameterDTO.hashCode(), differentIdFloatParameterDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "FloatParameterDTO{parameterTypeDefinitionId="
            + floatParameterDTO.getId()
            + ", defaultValue="
            + floatParameterDTO.getDefaultValue()
            + "}";
    assertEquals(expectedToString, floatParameterDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(10.5, floatParameterDTO.getDefaultValue());

    floatParameterDTO.setDefaultValue(20.0);

    assertEquals(20.0, floatParameterDTO.getDefaultValue());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(floatParameterDTO.equals(differentObject));
  }
}
