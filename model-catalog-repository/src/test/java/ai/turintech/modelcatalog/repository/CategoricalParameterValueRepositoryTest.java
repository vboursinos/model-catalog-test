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
public class CategoricalParameterValueRepositoryTest extends BasicRepositoryTest {
  @Autowired private CategoricalParameterValueRepository categoricalParameterValueRepository;

  private static final String CATEGORICAL_PARAMETER_VALUE_ID =
      "423e4567-e89b-12d3-a456-426614174003";

  private CategoricalParameterValue getCategoricalParameterValue() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValue categoricalParameterValue = new CategoricalParameterValue();
    categoricalParameterValue.setCategoricalParameter(categoricalParameter);
    categoricalParameterValue.setValue("test_value");
    return categoricalParameterValue;
  }

  private CategoricalParameterValue getUpdatedCategoricalParameterValue() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValue categoricalParameterValue = new CategoricalParameterValue();
    categoricalParameterValue.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    categoricalParameterValue.setCategoricalParameter(categoricalParameter);
    categoricalParameterValue.setValue("test_value");
    return categoricalParameterValue;
  }

  @Test
  void testFindAllCategoricalParameterValueRepository() {
    List<CategoricalParameterValue> categoricalParameterValues =
        categoricalParameterValueRepository.findAll();
    Assertions.assertEquals(4, categoricalParameterValues.size());
  }

  @Test
  void testFindByIdCategoricalParameterValueRepository() {
    CategoricalParameterValue categoricalParameterValue =
        categoricalParameterValueRepository
            .findById(UUID.fromString(CATEGORICAL_PARAMETER_VALUE_ID))
            .get();
    Assertions.assertEquals("Category3", categoricalParameterValue.getValue());
  }

  @Test
  void testSaveCategoricalParameterValueRepository() {
    CategoricalParameterValue savedCategoricalParameterValue =
        categoricalParameterValueRepository.save(getCategoricalParameterValue());
    Assertions.assertEquals(
        getCategoricalParameterValue().getValue(), savedCategoricalParameterValue.getValue());
    categoricalParameterValueRepository.delete(savedCategoricalParameterValue);
  }

  @Test
  void testUpdateCategoricalParameterValueRepository() {
    CategoricalParameterValue updatedCategoricalParameterValue =
        categoricalParameterValueRepository.save(getUpdatedCategoricalParameterValue());
    Assertions.assertEquals(
        getCategoricalParameterValue().getValue(), updatedCategoricalParameterValue.getValue());
  }
}
