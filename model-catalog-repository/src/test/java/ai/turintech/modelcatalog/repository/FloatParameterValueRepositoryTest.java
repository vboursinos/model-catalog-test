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
public class FloatParameterValueRepositoryTest extends BasicRepositoryTest {
  @Autowired private FloatParameterRangeRepository floatParameterRangeRepository;

  private FloatParameterRange getFloatParameterRange() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRange floatParameterRange = new FloatParameterRange();
    floatParameterRange.setFloatParameter(floatParameter);
    floatParameterRange.setLower(1.0);
    floatParameterRange.setUpper(10.0);
    floatParameterRange.setIsLeftOpen(false);
    floatParameterRange.setIsRightOpen(false);
    return floatParameterRange;
  }

  private FloatParameterRange getUpdatedFloatParameterRange() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRange floatParameterRange = new FloatParameterRange();
    floatParameterRange.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));
    floatParameterRange.setFloatParameter(floatParameter);
    floatParameterRange.setLower(1.0);
    floatParameterRange.setUpper(10.0);
    floatParameterRange.setIsLeftOpen(false);
    floatParameterRange.setIsRightOpen(false);
    return floatParameterRange;
  }

  @Test
  void testFindAllFloatParameterRangeRepository() {
    List<FloatParameterRange> floatParameterRanges = floatParameterRangeRepository.findAll();
    Assertions.assertEquals(4, floatParameterRanges.size());
  }

  @Test
  void testFindByIdFloatParameterRangeRepository() {
    FloatParameterRange floatParameterRange =
        floatParameterRangeRepository
            .findById(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"))
            .get();
    Assertions.assertEquals(25.3, floatParameterRange.getLower());
  }

  @Test
  void testSaveFloatParameterValueRepository() {
    FloatParameterRange savedFloatParameterRange =
        floatParameterRangeRepository.save(getFloatParameterRange());
    Assertions.assertEquals(
        getFloatParameterRange().getLower(), savedFloatParameterRange.getLower());
    floatParameterRangeRepository.delete(savedFloatParameterRange);
  }

  @Test
  void testUpdateFloatParameterValueRepository() {
    FloatParameterRange updatedFloatParameterRange =
        floatParameterRangeRepository.save(getUpdatedFloatParameterRange());
    Assertions.assertEquals(
        getUpdatedFloatParameterRange().getLower(), updatedFloatParameterRange.getLower());
  }
}
