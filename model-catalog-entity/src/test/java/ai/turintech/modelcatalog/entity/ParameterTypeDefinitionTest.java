package ai.turintech.modelcatalog.entity;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterTypeDefinitionTest {

  private ParameterTypeDefinition parameterTypeDefinition;

  @BeforeEach
  public void setUp() {
    parameterTypeDefinition = new ParameterTypeDefinition();
  }

  @Test
  public void testOrdering() {
    Integer ordering = 1;
    parameterTypeDefinition.setOrdering(ordering);
    Assertions.assertEquals(ordering, parameterTypeDefinition.getOrdering());
  }

  @Test
  public void testIntegerParameter() {
    IntegerParameter integerParameter = new IntegerParameter();
    parameterTypeDefinition.setIntegerParameter(integerParameter);
    Assertions.assertEquals(integerParameter, parameterTypeDefinition.getIntegerParameter());
  }

  @Test
  public void testFloatParameter() {
    FloatParameter floatParameter = new FloatParameter();
    parameterTypeDefinition.setFloatParameter(floatParameter);
    Assertions.assertEquals(floatParameter, parameterTypeDefinition.getFloatParameter());
  }

  @Test
  public void testCategoricalParameter() {
    CategoricalParameter categoricalParameter = new CategoricalParameter();
    parameterTypeDefinition.setCategoricalParameter(categoricalParameter);
    Assertions.assertEquals(
        categoricalParameter, parameterTypeDefinition.getCategoricalParameter());
  }

  @Test
  public void testBooleanParameter() {
    BooleanParameter booleanParameter = new BooleanParameter();
    parameterTypeDefinition.setBooleanParameter(booleanParameter);
    Assertions.assertEquals(booleanParameter, parameterTypeDefinition.getBooleanParameter());
  }

  @Test
  public void testDistribution() {
    ParameterDistributionType distribution = new ParameterDistributionType();
    parameterTypeDefinition.setDistribution(distribution);
    Assertions.assertEquals(distribution, parameterTypeDefinition.getDistribution());
  }

  @Test
  public void testParameter() {
    Parameter parameter = new Parameter();
    parameterTypeDefinition.setParameter(parameter);
    Assertions.assertEquals(parameter, parameterTypeDefinition.getParameter());
  }

  @Test
  public void testType() {
    ParameterType type = new ParameterType();
    parameterTypeDefinition.setType(type);
    Assertions.assertEquals(type, parameterTypeDefinition.getType());
  }

  @Test
  public void testEqualsAndHashcode() {
    Assertions.assertDoesNotThrow(
        () -> {
          ParameterTypeDefinition parameterTypeDefinition1 = new ParameterTypeDefinition();
          ParameterTypeDefinition parameterTypeDefinition2 = new ParameterTypeDefinition();
          UUID id = UUID.randomUUID();
          parameterTypeDefinition1.setId(id);
          parameterTypeDefinition2.setId(id);

          Assertions.assertEquals(parameterTypeDefinition1, parameterTypeDefinition2);
          Assertions.assertEquals(
              parameterTypeDefinition1.hashCode(), parameterTypeDefinition2.hashCode());
        });
  }
}
