package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.to.ParameterTO;
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
public class ParameterMapperTest {

  @Autowired private ParameterMapper parameterMapper;

  @Test
  public void testToTO() {
    ParameterDTO parameterDTO = createSampleParameterDTO();

    ParameterTO parameter = parameterMapper.to(parameterDTO);

    assertEquals(parameterDTO.getId(), parameter.getId());
    assertEquals(parameterDTO.getName(), parameter.getName());
    assertEquals(parameterDTO.getLabel(), parameter.getLabel());
  }

  @Test
  public void testToDTO() {
    ParameterTO parameterTO = createSampleParameterTO();

    ParameterDTO parameterDTO = parameterMapper.from(parameterTO);

    assertEquals(parameterTO.getId(), parameterDTO.getId());
    assertEquals(parameterTO.getName(), parameterDTO.getName());
    assertEquals(parameterTO.getLabel(), parameterDTO.getLabel());
  }

  @Test
  public void testPartialUpdate() {
    ParameterDTO updatedParameterDTO = createSampleParameterDTO();
    updatedParameterDTO.setName("Updated Parameter");

    ParameterTO parameterTO = createSampleParameterTO();

    parameterMapper.partialUpdate(updatedParameterDTO, parameterTO);
    assertEquals(updatedParameterDTO.getName(), parameterTO.getName());
  }

  private ParameterDTO createSampleParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setName("Sample Parameter");
    parameterDTO.setLabel("Sample Label");
    return parameterDTO;
  }

  private ParameterTO createSampleParameterTO() {
    ParameterTO parameterTO = new ParameterTO();
    parameterTO.setId(UUID.randomUUID());
    parameterTO.setName("Sample Parameter");
    parameterTO.setLabel("Sample Label");
    return parameterTO;
  }
}
