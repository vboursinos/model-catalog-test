package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterDistributionTypeTOTest {

  private ParameterDistributionTypeTO parameterDistributionTypeTO;

  @BeforeEach
  public void setUp() {
    parameterDistributionTypeTO = new ParameterDistributionTypeTO();
    parameterDistributionTypeTO.setId(UUID.randomUUID());
    parameterDistributionTypeTO.setName("TestDistributionType");
  }

  @Test
  public void testEquals() {
    ParameterDistributionTypeTO sameIdDistributionTypeTO = new ParameterDistributionTypeTO();
    sameIdDistributionTypeTO.setId(parameterDistributionTypeTO.getId());
    sameIdDistributionTypeTO.setName("TestDistributionType");

    ParameterDistributionTypeTO differentIdDistributionTypeTO = new ParameterDistributionTypeTO();
    differentIdDistributionTypeTO.setId(UUID.randomUUID());
    differentIdDistributionTypeTO.setName("DifferentTestDistributionType");

    assertEquals(parameterDistributionTypeTO, sameIdDistributionTypeTO);
    assertNotEquals(parameterDistributionTypeTO, differentIdDistributionTypeTO);
    assertNotEquals(parameterDistributionTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterDistributionTypeTO sameIdDistributionTypeTO = new ParameterDistributionTypeTO();
    sameIdDistributionTypeTO.setId(parameterDistributionTypeTO.getId());
    sameIdDistributionTypeTO.setName("TestDistributionType");

    ParameterDistributionTypeTO differentIdDistributionTypeTO = new ParameterDistributionTypeTO();
    differentIdDistributionTypeTO.setId(UUID.randomUUID());
    differentIdDistributionTypeTO.setName("DifferentTestDistributionType");

    assertEquals(parameterDistributionTypeTO.hashCode(), sameIdDistributionTypeTO.hashCode());
    assertNotEquals(
        parameterDistributionTypeTO.hashCode(), differentIdDistributionTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterDistributionTypeDTO{"
            + "id='"
            + parameterDistributionTypeTO.getId()
            + '\''
            + ", name='TestDistributionType'"
            + '}';
    assertEquals(expectedToString, parameterDistributionTypeTO.toString());
  }

  @Test
  public void testAccessorMethods() {
    assertEquals(parameterDistributionTypeTO.getId(), parameterDistributionTypeTO.getId());
    assertEquals("TestDistributionType", parameterDistributionTypeTO.getName());
  }

  @Test
  public void testNullId() {
    ParameterDistributionTypeTO nullIdDistributionTypeTO = new ParameterDistributionTypeTO();
    nullIdDistributionTypeTO.setId(null);
    assertNotEquals(parameterDistributionTypeTO, nullIdDistributionTypeTO);
  }
}
