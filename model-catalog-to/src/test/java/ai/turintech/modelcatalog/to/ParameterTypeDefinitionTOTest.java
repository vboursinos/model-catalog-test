package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterTypeDefinitionTOTest {

  private ParameterTypeDefinitionTO parameterTypeDefinitionTO;

  @BeforeEach
  public void setUp() {
    parameterTypeDefinitionTO = new ParameterTypeDefinitionTO();
    parameterTypeDefinitionTO.setId(UUID.randomUUID());
    parameterTypeDefinitionTO.setOrdering(1);
    parameterTypeDefinitionTO.setDistributionId(UUID.randomUUID());
    parameterTypeDefinitionTO.setParameterId(UUID.randomUUID());
    parameterTypeDefinitionTO.setTypeId(UUID.randomUUID());
    parameterTypeDefinitionTO.setDistribution(new ParameterDistributionTypeTO());
    parameterTypeDefinitionTO.setType(new ParameterTypeTO());
  }

  @Test
  public void testEquals() {
    ParameterTypeDefinitionTO sameIdParameterTypeDefinitionTO = new ParameterTypeDefinitionTO();
    sameIdParameterTypeDefinitionTO.setId(parameterTypeDefinitionTO.getId());
    sameIdParameterTypeDefinitionTO.setOrdering(1);
    sameIdParameterTypeDefinitionTO.setDistributionId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setParameterId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setTypeId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setDistribution(new ParameterDistributionTypeTO());
    sameIdParameterTypeDefinitionTO.setType(new ParameterTypeTO());

    ParameterTypeDefinitionTO differentIdParameterTypeDefinitionTO =
        new ParameterTypeDefinitionTO();
    differentIdParameterTypeDefinitionTO.setId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setOrdering(2);
    differentIdParameterTypeDefinitionTO.setDistributionId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setParameterId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setTypeId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setDistribution(new ParameterDistributionTypeTO());
    differentIdParameterTypeDefinitionTO.setType(new ParameterTypeTO());

    assertEquals(parameterTypeDefinitionTO, sameIdParameterTypeDefinitionTO);
    assertNotEquals(parameterTypeDefinitionTO, differentIdParameterTypeDefinitionTO);
    assertNotEquals(parameterTypeDefinitionTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterTypeDefinitionTO sameIdParameterTypeDefinitionTO = new ParameterTypeDefinitionTO();
    sameIdParameterTypeDefinitionTO.setId(parameterTypeDefinitionTO.getId());
    sameIdParameterTypeDefinitionTO.setOrdering(1);
    sameIdParameterTypeDefinitionTO.setDistributionId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setParameterId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setTypeId(UUID.randomUUID());
    sameIdParameterTypeDefinitionTO.setDistribution(new ParameterDistributionTypeTO());
    sameIdParameterTypeDefinitionTO.setType(new ParameterTypeTO());

    ParameterTypeDefinitionTO differentIdParameterTypeDefinitionTO =
        new ParameterTypeDefinitionTO();
    differentIdParameterTypeDefinitionTO.setId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setOrdering(2);
    differentIdParameterTypeDefinitionTO.setDistributionId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setParameterId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setTypeId(UUID.randomUUID());
    differentIdParameterTypeDefinitionTO.setDistribution(new ParameterDistributionTypeTO());
    differentIdParameterTypeDefinitionTO.setType(new ParameterTypeTO());

    assertEquals(parameterTypeDefinitionTO.hashCode(), sameIdParameterTypeDefinitionTO.hashCode());
    assertNotEquals(
        parameterTypeDefinitionTO.hashCode(), differentIdParameterTypeDefinitionTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterTypeDefinitionDTO{"
            + "id="
            + parameterTypeDefinitionTO.getId()
            + ", ordering=1"
            + ", distributionId="
            + parameterTypeDefinitionTO.getDistributionId()
            + ", parameterId="
            + parameterTypeDefinitionTO.getParameterId()
            + ", typeId="
            + parameterTypeDefinitionTO.getTypeId()
            + ", distribution="
            + parameterTypeDefinitionTO.getDistribution()
            + ", type="
            + parameterTypeDefinitionTO.getType()
            + '}';
    assertEquals(expectedToString, parameterTypeDefinitionTO.toString());
  }

  @Test
  public void testNullId() {
    ParameterTypeDefinitionTO nullIdParameterTypeDefinitionTO = new ParameterTypeDefinitionTO();
    nullIdParameterTypeDefinitionTO.setId(null);
    assertNotEquals(parameterTypeDefinitionTO, nullIdParameterTypeDefinitionTO);
  }
}
