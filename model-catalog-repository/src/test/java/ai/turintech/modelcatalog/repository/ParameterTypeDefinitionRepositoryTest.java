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
public class ParameterTypeDefinitionRepositoryTest extends BasicRepositoryTest {
  @Autowired private ParameterTypeDefinitionRepository parameterTypeDefinitionRepository;

  private ParameterTypeDefinition getParameterTypeDefinition() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174001"));
    parameter.setName("test_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionType.setName("parameterdistributiontype1");

    ParameterType parameterType = new ParameterType();
    parameterType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterType.setName("parametertype1");

    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setParameter(parameter);
    parameterTypeDefinition.setDistribution(parameterDistributionType);
    parameterTypeDefinition.setType(parameterType);
    parameterTypeDefinition.setOrdering(10);

    return parameterTypeDefinition;
  }

  private ParameterTypeDefinition getUpdatedParameterTypeDefinition() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174001"));
    parameter.setName("test_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionType.setName("parameterdistributiontype1");

    ParameterType parameterType = new ParameterType();
    parameterType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterType.setName("parametertype1");

    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setParameter(parameter);
    parameterTypeDefinition.setDistribution(parameterDistributionType);
    parameterTypeDefinition.setType(parameterType);
    parameterTypeDefinition.setOrdering(12);

    return parameterTypeDefinition;
  }

  @Test
  void testFindAllParameterTypeDefinitionRepository() {
    List<ParameterTypeDefinition> parameterTypeDefinitions =
        parameterTypeDefinitionRepository.findAll();
    Assertions.assertEquals(2, parameterTypeDefinitions.size());
  }

  @Test
  void testFindByIdParameterTypeDefinitionRepository() {
    ParameterTypeDefinition parameterTypeDefinition =
        parameterTypeDefinitionRepository
            .findById(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"))
            .get();
    Assertions.assertEquals(1, parameterTypeDefinition.getOrdering());
  }

  @Test
  void testSaveParameterTypeDefinitionRepository() {
    ParameterTypeDefinition savedParameterTypeDefinition =
        parameterTypeDefinitionRepository.save(getParameterTypeDefinition());
    Assertions.assertEquals(
        getParameterTypeDefinition().getOrdering(), savedParameterTypeDefinition.getOrdering());
    parameterTypeDefinitionRepository.delete(savedParameterTypeDefinition);
  }

  @Test
  void testUpdateParameterTypeDefinitionRepository() {
    ParameterTypeDefinition updateParameterTypeDefinition =
        parameterTypeDefinitionRepository.save(getUpdatedParameterTypeDefinition());
    Assertions.assertEquals(
        getUpdatedParameterTypeDefinition().getOrdering(),
        updateParameterTypeDefinition.getOrdering());
  }
}
