package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicparametervalidation;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.CompareParametersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CompareParameterServiceTest {
  private CompareParametersServiceImpl compareParametersService =
      new CompareParametersServiceImpl();

  private static ParameterTypeDistribution parameterTypeDistribution;
  private static ParameterTypeDefinitionDTO dbParameterTypeDefinition;

  private static HyperParameter hyperParameter;

  private static ParameterDTO parameterDTO;

  private static String parameterName;

  @BeforeAll
  public static void setUpParameterTypeDefinition() {
    parameterName = "parameterNameTest";
    parameterTypeDistribution = new ParameterTypeDistribution();
    parameterTypeDistribution.setParameterType("paramTypeTest");
    parameterTypeDistribution.setParameterDistribution("parameterDistributionTest");
    parameterTypeDistribution.setParameterName(parameterName);

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("paramTypeTest");
    dbParameterTypeDefinition = new ParameterTypeDefinitionDTO();
    dbParameterTypeDefinition.setType(parameterTypeDTO);
  }

  @BeforeAll
  public static void setUpParameters() {
    hyperParameter = new HyperParameter();
    hyperParameter.setDescription("descriptionTest");
    hyperParameter.setLabel("labelTest");
    hyperParameter.setFixedValue(true);
    hyperParameter.setEnabled(true);

    parameterDTO = new ParameterDTO();
    parameterDTO.setLabel("labelTest");
    parameterDTO.setFixedValue(true);
    parameterDTO.setEnabled(true);
  }

  @Test
  public void compareParameterTypeDefinitionTestTrue() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("parameterDistributionTest");
    dbParameterTypeDefinition.setDistribution(parameterDistributionTypeDTO);
    boolean result =
        compareParametersService.compareParameterTypeDefinition(
            parameterTypeDistribution, dbParameterTypeDefinition, parameterName);
    Assertions.assertTrue(result);
  }

  @Test
  public void compareParameterTypeDefinitionTestFalse() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("parameterDistributionTestFalse");
    dbParameterTypeDefinition.setDistribution(parameterDistributionTypeDTO);

    boolean result =
        compareParametersService.compareParameterTypeDefinition(
            parameterTypeDistribution, dbParameterTypeDefinition, parameterName);
    Assertions.assertFalse(result);
  }

  @Test
  public void compareParameterColumnsTestTrue() {
    parameterDTO.setDescription("descriptionTest");
    boolean result = compareParametersService.compareParameterColumns(hyperParameter, parameterDTO);
    Assertions.assertTrue(result);
  }

  @Test
  public void compareParameterColumnsTestFalse() {
    parameterDTO.setDescription("descriptionTestFalse");
    boolean result = compareParametersService.compareParameterColumns(hyperParameter, parameterDTO);
    Assertions.assertFalse(result);
  }
}
