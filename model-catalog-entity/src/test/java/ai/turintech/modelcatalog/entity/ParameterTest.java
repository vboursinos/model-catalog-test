package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterTest {

  private final Parameter parameterUnderTest = new Parameter();
  private final Model model = new Model();
  private final ParameterTypeDefinition definition = new ParameterTypeDefinition();

  @BeforeEach
  public void setUp() {

    UUID id = UUID.randomUUID();
    parameterUnderTest.setId(id);
    parameterUnderTest.setName("Test");
    parameterUnderTest.setLabel("Test Label");
    parameterUnderTest.setDescription("This is a test parameter");
    parameterUnderTest.setEnabled(true);
    parameterUnderTest.setFixedValue(true);
    parameterUnderTest.setOrdering(1);
  }

  @Test
  public void testGetSetMethods() {
    String resultName = parameterUnderTest.getName();
    assertThat(resultName).isEqualTo("Test");

    String resultLabel = parameterUnderTest.getLabel();
    assertThat(resultLabel).isEqualTo("Test Label");

    String resultDescription = parameterUnderTest.getDescription();
    assertThat(resultDescription).isEqualTo("This is a test parameter");

    Boolean resultEnabled = parameterUnderTest.getEnabled();
    assertThat(resultEnabled).isEqualTo(true);

    Boolean resultFixedValue = parameterUnderTest.getFixedValue();
    assertThat(resultFixedValue).isEqualTo(true);

    Integer resultOrdering = parameterUnderTest.getOrdering();
    assertThat(resultOrdering).isEqualTo(1);
  }

  @Test
  public void testGetSetModelsAndDefinitions() {
    parameterUnderTest.setDefinitions(Set.of(definition));
    assertThat(parameterUnderTest.getDefinitions()).contains(definition);

    parameterUnderTest.setModel(model);
    assertThat(parameterUnderTest.getModel()).isEqualTo(model);
  }

  @Test
  public void testEquals() {
    Parameter entityToCompare = new Parameter();
    entityToCompare.setId(parameterUnderTest.getId());
    entityToCompare.setName(parameterUnderTest.getName());

    assertThat(parameterUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = parameterUnderTest.hashCode();
    assertThat(result).isEqualTo(Parameter.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = parameterUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "Parameter{id="
                + parameterUnderTest.getId()
                + ", name='"
                + parameterUnderTest.getName()
                + "', label='"
                + parameterUnderTest.getLabel()
                + "', description='"
                + parameterUnderTest.getDescription()
                + "', enabled='"
                + parameterUnderTest.getEnabled()
                + "', fixedValue='"
                + parameterUnderTest.getFixedValue()
                + "', ordering="
                + parameterUnderTest.getOrdering()
                + "}");
  }
}
