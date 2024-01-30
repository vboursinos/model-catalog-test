package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class IntegerParameterMapperTest {

  @Autowired private IntegerParameterMapper integerParameterMapper;

  @Test
  public void testToEntity() {
    IntegerParameterDTO parameterDTO = new IntegerParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setDefaultValue(10);

    IntegerParameter integerParameter = integerParameterMapper.from(parameterDTO);

    assertEquals(parameterDTO.getId(), integerParameter.getId());
    assertEquals(parameterDTO.getDefaultValue(), integerParameter.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.randomUUID());
    integerParameter.setDefaultValue(20);

    IntegerParameterDTO parameterDTO = integerParameterMapper.to(integerParameter);

    assertEquals(integerParameter.getId(), parameterDTO.getId());
    assertEquals(integerParameter.getDefaultValue(), parameterDTO.getDefaultValue());
  }

  @Test
  public void testPartialUpdate() {
    IntegerParameterDTO parameterDTO = new IntegerParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setDefaultValue(30);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(parameterDTO.getId());
    integerParameter.setDefaultValue(40);

    integerParameterMapper.partialUpdate(integerParameter, parameterDTO);

    assertEquals(parameterDTO.getDefaultValue(), integerParameter.getDefaultValue());
  }
}
