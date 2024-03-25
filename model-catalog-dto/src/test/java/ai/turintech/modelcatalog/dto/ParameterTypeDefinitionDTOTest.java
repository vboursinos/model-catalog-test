package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParameterTypeDefinitionDTOTest {

  private ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;

  @BeforeAll
  public void setUp() {
    parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setOrdering(1);
    parameterTypeDefinitionDTO.setDistributionId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setParameterId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setTypeId(UUID.randomUUID());

    ParameterDistributionTypeDTO distribution = new ParameterDistributionTypeDTO();
    distribution.setId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setDistribution(distribution);

    ParameterTypeDTO type = new ParameterTypeDTO();
    type.setId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setType(type);
  }

  @Test
  public void testEquals() {
    ParameterTypeDefinitionDTO sameIdParameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    sameIdParameterTypeDefinitionDTO.setId(parameterTypeDefinitionDTO.getId());

    ParameterTypeDefinitionDTO differentIdParameterTypeDefinitionDTO =
        new ParameterTypeDefinitionDTO();
    differentIdParameterTypeDefinitionDTO.setId(UUID.randomUUID());

    assertEquals(parameterTypeDefinitionDTO, sameIdParameterTypeDefinitionDTO);
    assertNotEquals(parameterTypeDefinitionDTO, differentIdParameterTypeDefinitionDTO);
    assertNotEquals(parameterTypeDefinitionDTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterTypeDefinitionDTO sameIdParameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    sameIdParameterTypeDefinitionDTO.setId(parameterTypeDefinitionDTO.getId());

    ParameterTypeDefinitionDTO differentIdParameterTypeDefinitionDTO =
        new ParameterTypeDefinitionDTO();
    differentIdParameterTypeDefinitionDTO.setId(UUID.randomUUID());

    assertEquals(
        parameterTypeDefinitionDTO.hashCode(), sameIdParameterTypeDefinitionDTO.hashCode());
    assertNotEquals(
        parameterTypeDefinitionDTO.hashCode(), differentIdParameterTypeDefinitionDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterTypeDefinitionDTO{id="
            + parameterTypeDefinitionDTO.getId()
            + ", ordering=1, distributionId="
            + parameterTypeDefinitionDTO.getDistributionId()
            + ", parameterId="
            + parameterTypeDefinitionDTO.getParameterId()
            + ", typeId="
            + parameterTypeDefinitionDTO.getTypeId()
            + ", distribution="
            + parameterTypeDefinitionDTO.getDistribution()
            + ", type="
            + parameterTypeDefinitionDTO.getType()
            + '}';
    assertEquals(expectedToString, parameterTypeDefinitionDTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals(1, parameterTypeDefinitionDTO.getOrdering());
    assertEquals(UUID.class, parameterTypeDefinitionDTO.getDistributionId().getClass());
    assertEquals(UUID.class, parameterTypeDefinitionDTO.getParameterId().getClass());
    assertEquals(UUID.class, parameterTypeDefinitionDTO.getTypeId().getClass());
    assertNotNull(parameterTypeDefinitionDTO.getDistribution());
    assertNotNull(parameterTypeDefinitionDTO.getType());

    parameterTypeDefinitionDTO.setOrdering(2);
    UUID newDistributionId = UUID.randomUUID();
    parameterTypeDefinitionDTO.setDistributionId(newDistributionId);
    UUID newParameterId = UUID.randomUUID();
    parameterTypeDefinitionDTO.setParameterId(newParameterId);
    UUID newTypeId = UUID.randomUUID();
    parameterTypeDefinitionDTO.setTypeId(newTypeId);

    assertEquals(2, parameterTypeDefinitionDTO.getOrdering());
    assertEquals(newDistributionId, parameterTypeDefinitionDTO.getDistributionId());
    assertEquals(newParameterId, parameterTypeDefinitionDTO.getParameterId());
    assertEquals(newTypeId, parameterTypeDefinitionDTO.getTypeId());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(parameterTypeDefinitionDTO.equals(differentObject));
  }
}
