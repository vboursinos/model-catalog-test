package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class IntegerParameterRepositoryTest {
  @Autowired private IntegerParameterRepository integerParameterRepository;

  private static final String INTEGER_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_ID = "523e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_DISTRIBUTION_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_TYPE_DEFINITION_ID = "323e4567-e89b-12d3-a456-426614174003";

  private IntegerParameter getIntegerParameter() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.fromString(PARAMETER_ID));

    ParameterType parameterType = new ParameterType();
    parameterType.setId(UUID.fromString(PARAMETER_TYPE_ID));

    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.fromString(PARAMETER_DISTRIBUTION_TYPE_ID));

    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString(PARAMETER_TYPE_DEFINITION_ID));
    parameterTypeDefinition.setParameter(parameter);
    parameterTypeDefinition.setType(parameterType);
    parameterTypeDefinition.setDistribution(parameterDistributionType);
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);
    return integerParameter;
  }

  @Test
  void testFindAllIntegerParameterRepository() {
    List<IntegerParameter> integerParameters = integerParameterRepository.findAll();
    Assertions.assertEquals(2, integerParameters.size());
  }

  @Test
  void testFindByIdIntegerParameterRepository() {
    IntegerParameter integerParameter =
        integerParameterRepository.findById(UUID.fromString(INTEGER_PARAMETER_ID)).get();
    Assertions.assertEquals(10, integerParameter.getDefaultValue());
  }
}
