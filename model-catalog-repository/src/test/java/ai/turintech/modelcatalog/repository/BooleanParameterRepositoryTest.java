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
public class BooleanParameterRepositoryTest extends BasicRepositoryTest {
  @Autowired private BooleanParameterRepository booleanParameterRepository;

  private static final String BOOLEAN_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_ID = "523e4567-e89b-12d3-a456-426614174001";
  private static final String PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_DISTRIBUTION_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_DEFINITION_TYPE_ID = "323e4567-e89b-12d3-a456-426614174003";

  private BooleanParameter getBooleanParameter() {
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

    BooleanParameter booleanParameter = new BooleanParameter();
    booleanParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    booleanParameter.setDefaultValue(false);
    booleanParameter.setParameterTypeDefinition(parameterTypeDefinition);
    return booleanParameter;
  }

  @Test
  void testFindAllBooleanParameterRepository() {
    List<BooleanParameter> booleanParameters = booleanParameterRepository.findAll();
    Assertions.assertEquals(2, booleanParameters.size());
  }

  @Test
  void testFindByIdBooleanParameterRepository() {
    BooleanParameter booleanParameter =
        booleanParameterRepository.findById(UUID.fromString(BOOLEAN_PARAMETER_ID)).get();
    Assertions.assertEquals(true, booleanParameter.getDefaultValue());
  }
}
