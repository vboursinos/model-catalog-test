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
public class IntegerParameterValueRepositoryTest extends BasicRepositoryTest {
  @Autowired private IntegerParameterValueRepository integerParameterValueRepository;

  private IntegerParameterValue getIntegerParameterValue() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValue integerParameterValue = new IntegerParameterValue();
    integerParameterValue.setIntegerParameter(integerParameter);
    integerParameterValue.setLower(1);
    integerParameterValue.setUpper(10);
    return integerParameterValue;
  }

  private IntegerParameterValue getUpdatedIntegerParameterValue() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValue integerParameterValue = new IntegerParameterValue();
    integerParameterValue.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    integerParameterValue.setIntegerParameter(integerParameter);
    integerParameterValue.setLower(1);
    integerParameterValue.setUpper(10);
    return integerParameterValue;
  }

  @Test
  void testFindAllIntegerParameterValueRepository() {
    List<IntegerParameterValue> integerParameterValues = integerParameterValueRepository.findAll();
    Assertions.assertEquals(4, integerParameterValues.size());
  }

  @Test
  void testFindByIdIntegerParameterValueRepository() {
    IntegerParameterValue integerParameterValue =
        integerParameterValueRepository
            .findById(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"))
            .get();
    Assertions.assertEquals(25, integerParameterValue.getLower());
  }

  @Test
  void testSaveIntegerParameterValueRepository() {
    IntegerParameterValue savedIntegerParameterValue =
        integerParameterValueRepository.save(getIntegerParameterValue());
    Assertions.assertEquals(
        getIntegerParameterValue().getLower(), savedIntegerParameterValue.getLower());
    integerParameterValueRepository.delete(savedIntegerParameterValue);
  }

  @Test
  void testUpdateIntegerParameterValueRepository() {
    IntegerParameterValue updatedIntegerParameterValue =
        integerParameterValueRepository.save(getUpdatedIntegerParameterValue());
    Assertions.assertEquals(
        getIntegerParameterValue().getLower(), updatedIntegerParameterValue.getLower());
  }
}
