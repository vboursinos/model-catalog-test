package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class IntegerParameterValueMapperTest {

  @Autowired private IntegerParameterValueMapper integerParameterValueMapper;

  @Test
  public void testToEntity() {
    IntegerParameterValueDTO parameterValueDTO = new IntegerParameterValueDTO();
    parameterValueDTO.setId(UUID.randomUUID());
    parameterValueDTO.setLower(10);
    parameterValueDTO.setUpper(20);

    IntegerParameterValue integerParameterValue =
        integerParameterValueMapper.from(parameterValueDTO);

    assertEquals(parameterValueDTO.getId(), integerParameterValue.getId());
    assertEquals(parameterValueDTO.getLower(), integerParameterValue.getLower());
    assertEquals(parameterValueDTO.getUpper(), integerParameterValue.getUpper());
  }

  @Test
  public void testToDTO() {
    IntegerParameterValue integerParameterValue = new IntegerParameterValue();
    integerParameterValue.setId(UUID.randomUUID());
    integerParameterValue.setLower(30);
    integerParameterValue.setUpper(40);

    IntegerParameterValueDTO parameterValueDTO =
        integerParameterValueMapper.to(integerParameterValue);

    assertEquals(integerParameterValue.getId(), parameterValueDTO.getId());
    assertEquals(integerParameterValue.getLower(), parameterValueDTO.getLower());
    assertEquals(integerParameterValue.getUpper(), parameterValueDTO.getUpper());
  }

  @Test
  public void testPartialUpdate() {
    IntegerParameterValueDTO parameterValueDTO = new IntegerParameterValueDTO();
    parameterValueDTO.setId(UUID.randomUUID());
    parameterValueDTO.setLower(50);
    parameterValueDTO.setUpper(60);

    IntegerParameterValue integerParameterValue = new IntegerParameterValue();
    integerParameterValue.setId(parameterValueDTO.getId());
    integerParameterValue.setLower(70);
    integerParameterValue.setUpper(80);

    integerParameterValueMapper.partialUpdate(integerParameterValue, parameterValueDTO);

    assertEquals(parameterValueDTO.getLower(), integerParameterValue.getLower());
    assertEquals(parameterValueDTO.getUpper(), integerParameterValue.getUpper());
  }
}
