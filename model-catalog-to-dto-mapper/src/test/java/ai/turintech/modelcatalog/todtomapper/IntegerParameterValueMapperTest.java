package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.to.IntegerParameterValueTO;
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
public class IntegerParameterValueMapperTest {

  @Autowired private IntegerParameterValueMapper integerParameterValueMapper;

  @Test
  public void testToTO() {
    IntegerParameterValueDTO parameterValueDTO = new IntegerParameterValueDTO();
    parameterValueDTO.setId(UUID.randomUUID());
    parameterValueDTO.setLower(10);
    parameterValueDTO.setUpper(20);

    IntegerParameterValueTO integerParameterValue =
        integerParameterValueMapper.to(parameterValueDTO);

    assertEquals(parameterValueDTO.getId(), integerParameterValue.getId());
    assertEquals(parameterValueDTO.getLower(), integerParameterValue.getLower());
    assertEquals(parameterValueDTO.getUpper(), integerParameterValue.getUpper());
  }

  @Test
  public void testToDTO() {
    IntegerParameterValueTO integerParameterValueTO = new IntegerParameterValueTO();
    integerParameterValueTO.setId(UUID.randomUUID());
    integerParameterValueTO.setLower(30);
    integerParameterValueTO.setUpper(40);

    IntegerParameterValueDTO parameterValueDTO =
        integerParameterValueMapper.from(integerParameterValueTO);

    assertEquals(integerParameterValueTO.getId(), parameterValueDTO.getId());
    assertEquals(integerParameterValueTO.getLower(), parameterValueDTO.getLower());
    assertEquals(integerParameterValueTO.getUpper(), parameterValueDTO.getUpper());
  }

  @Test
  public void testPartialUpdate() {
    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setId(UUID.randomUUID());
    integerParameterValueDTO.setLower(50);
    integerParameterValueDTO.setUpper(60);

    IntegerParameterValueTO integerParameterValueTO = new IntegerParameterValueTO();
    integerParameterValueTO.setId(integerParameterValueDTO.getId());
    integerParameterValueTO.setLower(70);
    integerParameterValueTO.setUpper(80);

    integerParameterValueMapper.partialUpdate(integerParameterValueDTO, integerParameterValueTO);

    assertEquals(integerParameterValueDTO.getLower(), integerParameterValueTO.getLower());
    assertEquals(integerParameterValueDTO.getUpper(), integerParameterValueTO.getUpper());
  }
}
