package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class FloatParameterRepositoryTest extends BasicRepositoryTest {
  @Autowired private FloatParameterRepository floatParameterRepository;

  private static final String FLOAT_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_ID = "523e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_DISTRIBUTION_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_DEFINITION_TYPE_ID = "323e4567-e89b-12d3-a456-426614174003";

  private FloatParameter getFloatParameter() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.fromString(PARAMETER_ID));

    ParameterType parameterType = new ParameterType();
    parameterType.setId(UUID.fromString(PARAMETER_TYPE_ID));

    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.fromString(PARAMETER_DISTRIBUTION_TYPE_ID));

    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString(PARAMETER_DEFINITION_TYPE_ID));
    parameterTypeDefinition.setParameter(parameter);
    parameterTypeDefinition.setType(parameterType);
    parameterTypeDefinition.setDistribution(parameterDistributionType);
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);
    return floatParameter;
  }

  @Test
  void testFindAllFloatParameterRepository() {
    List<FloatParameter> floatParameters = floatParameterRepository.findAll();
    Assertions.assertEquals(2, floatParameters.size());
  }

  @Test
  void testFindByIdFloatParameterRepository() {
    FloatParameter floatParameter =
        floatParameterRepository.findById(UUID.fromString(FLOAT_PARAMETER_ID)).get();
    Assertions.assertEquals(10.1, floatParameter.getDefaultValue());
  }
}
