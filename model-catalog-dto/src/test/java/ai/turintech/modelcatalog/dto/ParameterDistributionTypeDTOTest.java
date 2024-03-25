package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParameterDistributionTypeDTOTest {

  private ParameterDistributionTypeDTO parameterDistributionTypeDTO;

  @BeforeAll
  public void setUp() {
    parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.randomUUID());
    parameterDistributionTypeDTO.setName("TestDistributionType");
  }

  @Test
  public void testEquals() {
    ParameterDistributionTypeDTO sameIdDistributionTypeDTO = new ParameterDistributionTypeDTO();
    sameIdDistributionTypeDTO.setId(parameterDistributionTypeDTO.getId());

    ParameterDistributionTypeDTO differentIdDistributionTypeDTO =
        new ParameterDistributionTypeDTO();
    differentIdDistributionTypeDTO.setId(UUID.randomUUID());

    assertEquals(parameterDistributionTypeDTO, sameIdDistributionTypeDTO);
    assertNotEquals(parameterDistributionTypeDTO, differentIdDistributionTypeDTO);
    assertNotEquals(parameterDistributionTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterDistributionTypeDTO sameIdDistributionTypeDTO = new ParameterDistributionTypeDTO();
    sameIdDistributionTypeDTO.setId(parameterDistributionTypeDTO.getId());

    ParameterDistributionTypeDTO differentIdDistributionTypeDTO =
        new ParameterDistributionTypeDTO();
    differentIdDistributionTypeDTO.setId(UUID.randomUUID());

    assertEquals(parameterDistributionTypeDTO.hashCode(), sameIdDistributionTypeDTO.hashCode());
    assertNotEquals(
        parameterDistributionTypeDTO.hashCode(), differentIdDistributionTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterDistributionTypeDTO{id='"
            + parameterDistributionTypeDTO.getId()
            + "', name='TestDistributionType'}";
    assertEquals(expectedToString, parameterDistributionTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ParameterDistributionTypeDTO newDistributionTypeDTO = new ParameterDistributionTypeDTO();
    newDistributionTypeDTO.setName("NewTestDistributionType");

    assertEquals("NewTestDistributionType", newDistributionTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(parameterDistributionTypeDTO.equals(differentObject));
  }
}
