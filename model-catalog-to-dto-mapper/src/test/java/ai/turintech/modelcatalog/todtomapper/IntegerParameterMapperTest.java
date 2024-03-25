package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.to.IntegerParameterTO;
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
  public void testToTO() {
    IntegerParameterDTO parameterDTO = new IntegerParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setDefaultValue(10);

    IntegerParameterTO integerParameterTO = integerParameterMapper.to(parameterDTO);

    assertEquals(parameterDTO.getId(), integerParameterTO.getId());
    assertEquals(parameterDTO.getDefaultValue(), integerParameterTO.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    IntegerParameterTO integerParameterTO = new IntegerParameterTO();
    integerParameterTO.setId(UUID.randomUUID());
    integerParameterTO.setDefaultValue(20);

    IntegerParameterDTO parameterDTO = integerParameterMapper.from(integerParameterTO);

    assertEquals(integerParameterTO.getId(), parameterDTO.getId());
    assertEquals(integerParameterTO.getDefaultValue(), parameterDTO.getDefaultValue());
  }

  @Test
  public void testPartialUpdate() {
    IntegerParameterDTO parameterDTO = new IntegerParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setDefaultValue(30);

    IntegerParameterTO integerParameterTO = new IntegerParameterTO();
    integerParameterTO.setId(parameterDTO.getId());
    integerParameterTO.setDefaultValue(40);

    integerParameterMapper.partialUpdate(parameterDTO, integerParameterTO);

    assertEquals(parameterDTO.getDefaultValue(), integerParameterTO.getDefaultValue());
  }
}
