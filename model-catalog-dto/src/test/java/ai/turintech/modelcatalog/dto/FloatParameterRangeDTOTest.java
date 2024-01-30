package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FloatParameterRangeDTOTest {

  private FloatParameterRangeDTO floatParameterRangeDTO;

  @BeforeEach
  public void setUp() {
    floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setId(UUID.randomUUID());
    floatParameterRangeDTO.setIsLeftOpen(true);
    floatParameterRangeDTO.setIsRightOpen(false);
    floatParameterRangeDTO.setLower(5.0);
    floatParameterRangeDTO.setUpper(10.0);
  }

  @Test
  public void testEquals() {
    FloatParameterRangeDTO sameIdFloatParameterRangeDTO = new FloatParameterRangeDTO();
    sameIdFloatParameterRangeDTO.setId(floatParameterRangeDTO.getId());

    FloatParameterRangeDTO differentIdFloatParameterRangeDTO = new FloatParameterRangeDTO();
    differentIdFloatParameterRangeDTO.setId(UUID.randomUUID());

    assertEquals(floatParameterRangeDTO, sameIdFloatParameterRangeDTO);
    assertNotEquals(floatParameterRangeDTO, differentIdFloatParameterRangeDTO);
    assertNotEquals(floatParameterRangeDTO, null);
  }

  @Test
  public void testHashCode() {
    FloatParameterRangeDTO sameIdFloatParameterRangeDTO = new FloatParameterRangeDTO();
    sameIdFloatParameterRangeDTO.setId(floatParameterRangeDTO.getId());

    FloatParameterRangeDTO differentIdFloatParameterRangeDTO = new FloatParameterRangeDTO();
    differentIdFloatParameterRangeDTO.setId(UUID.randomUUID());

    assertEquals(floatParameterRangeDTO.hashCode(), sameIdFloatParameterRangeDTO.hashCode());
    assertNotEquals(
        floatParameterRangeDTO.hashCode(), differentIdFloatParameterRangeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "FloatParameterRangeDTO{id="
            + floatParameterRangeDTO.getId()
            + ", isLeftOpen='"
            + floatParameterRangeDTO.getIsLeftOpen()
            + "', isRightOpen='"
            + floatParameterRangeDTO.getIsRightOpen()
            + "', lower="
            + floatParameterRangeDTO.getLower()
            + ", upper="
            + floatParameterRangeDTO.getUpper()
            + "}";
    assertEquals(expectedToString, floatParameterRangeDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertTrue(floatParameterRangeDTO.getIsLeftOpen());
    assertFalse(floatParameterRangeDTO.getIsRightOpen());
    assertEquals(5.0, floatParameterRangeDTO.getLower());
    assertEquals(10.0, floatParameterRangeDTO.getUpper());

    floatParameterRangeDTO.setIsLeftOpen(false);
    floatParameterRangeDTO.setIsRightOpen(true);
    floatParameterRangeDTO.setLower(0.0);
    floatParameterRangeDTO.setUpper(15.0);

    assertFalse(floatParameterRangeDTO.getIsLeftOpen());
    assertTrue(floatParameterRangeDTO.getIsRightOpen());
    assertEquals(0.0, floatParameterRangeDTO.getLower());
    assertEquals(15.0, floatParameterRangeDTO.getUpper());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(floatParameterRangeDTO.equals(differentObject));
  }
}
