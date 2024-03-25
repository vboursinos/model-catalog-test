package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterDistributionTypeTest {

  private final ParameterDistributionType parameterDistributionTypeUnderTest =
      new ParameterDistributionType();
  private final ParameterTypeDefinition definition = new ParameterTypeDefinition();

  @BeforeEach
  public void setUp() {
    UUID id = UUID.randomUUID();
    parameterDistributionTypeUnderTest.setId(id);
    parameterDistributionTypeUnderTest.setName("Test");
  }

  @Test
  public void testGetName() {
    String result = parameterDistributionTypeUnderTest.getName();
    assertThat(result).isEqualTo("Test");
  }

  @Test
  public void testSetName() {
    parameterDistributionTypeUnderTest.setName("UpdatedTest");

    String result = parameterDistributionTypeUnderTest.getName();
    assertThat(result).isEqualTo("UpdatedTest");
  }

  @Test
  public void testGetSetDefinitions() {
    parameterDistributionTypeUnderTest.setDefinitions(Set.of(definition));
    assertThat(parameterDistributionTypeUnderTest.getDefinitions()).contains(definition);
  }

  @Test
  public void testEquals() {
    ParameterDistributionType entityToCompare = new ParameterDistributionType();
    entityToCompare.setId(parameterDistributionTypeUnderTest.getId());
    entityToCompare.setName(parameterDistributionTypeUnderTest.getName());

    assertThat(parameterDistributionTypeUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = parameterDistributionTypeUnderTest.hashCode();
    assertThat(result).isEqualTo(ParameterDistributionType.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = parameterDistributionTypeUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "ParameterDistributionType{id="
                + parameterDistributionTypeUnderTest.getId()
                + ", name='"
                + parameterDistributionTypeUnderTest.getName()
                + "'}");
  }
}
