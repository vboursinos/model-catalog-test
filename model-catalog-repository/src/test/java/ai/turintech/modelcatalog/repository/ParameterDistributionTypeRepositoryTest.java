package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import java.util.List;
import java.util.NoSuchElementException;
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
public class ParameterDistributionTypeRepositoryTest {
  @Autowired private ParameterDistributionTypeRepository parameterDistributionTypeRepository;

  private static final String PARAMETER_DISTRIBUTION_TYPE_ID =
      "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String NEW_PARAMETER_DISTRIBUTION_TYPE_ID =
      "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ParameterDistributionType getParameterDistributionType() {
    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setName("test_parameterDistributionType");
    return parameterDistributionType;
  }

  private ParameterDistributionType getUpdatedParameterDistributionType() {
    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.fromString(NEW_PARAMETER_DISTRIBUTION_TYPE_ID));
    parameterDistributionType.setName("test_updated_parameterDistributionType");
    return parameterDistributionType;
  }

  @Test
  void testFindAllParameterDistributionTypeRepository() {
    List<ParameterDistributionType> parameterDistributionTypes =
        parameterDistributionTypeRepository.findAll();
    Assertions.assertEquals(4, parameterDistributionTypes.size());
  }

  @Test
  void testFindByIdParameterDistributionTypeRepository() {
    ParameterDistributionType parameterDistributionType =
        parameterDistributionTypeRepository
            .findById(UUID.fromString(PARAMETER_DISTRIBUTION_TYPE_ID))
            .orElseThrow(() -> new NoSuchElementException("Parameter distribution type not found"));
    Assertions.assertEquals("parameterdistributiontype3", parameterDistributionType.getName());
  }

  @Test
  void testSaveParameterDistributionTypeRepository() {
    ParameterDistributionType savedParameterDistributionType =
        parameterDistributionTypeRepository.save(getParameterDistributionType());
    Assertions.assertEquals(
        getParameterDistributionType().getName(), savedParameterDistributionType.getName());
    parameterDistributionTypeRepository.delete(savedParameterDistributionType);
  }

  @Test
  void testUpdateParameterDistributionTypeRepository() {
    ParameterDistributionType updatedParameterDistributionType =
        parameterDistributionTypeRepository.save(getUpdatedParameterDistributionType());
    Assertions.assertEquals(
        getUpdatedParameterDistributionType().getName(),
        updatedParameterDistributionType.getName());
  }
}
