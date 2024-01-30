package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.Parameter;
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
  public void testToEntity() {
    ParameterDTO parameterDTO = createSampleParameterDTO();

    Parameter parameter = parameterMapper.from(parameterDTO);

    assertEquals(parameterDTO.getId(), parameter.getId());
    assertEquals(parameterDTO.getName(), parameter.getName());
    assertEquals(parameterDTO.getLabel(), parameter.getLabel());
  }

  @Test
  public void testToDTO() {
    Parameter parameter = createSampleParameterEntity();

    ParameterDTO parameterDTO = parameterMapper.to(parameter);

    assertEquals(parameter.getId(), parameterDTO.getId());
    assertEquals(parameter.getName(), parameterDTO.getName());
    assertEquals(parameter.getLabel(), parameterDTO.getLabel());
  }

  @Test
  public void testPartialUpdate() {
    ParameterDTO updatedParameterDTO = createSampleParameterDTO();
    updatedParameterDTO.setName("Updated Parameter");

    Parameter parameter = createSampleParameterEntity();

    parameterMapper.partialUpdate(parameter, updatedParameterDTO);
    assertEquals(updatedParameterDTO.getName(), parameter.getName());
  }

  private ParameterDTO createSampleParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setId(UUID.randomUUID());
    parameterDTO.setName("Sample Parameter");
    parameterDTO.setLabel("Sample Label");
    return parameterDTO;
  }

  private Parameter createSampleParameterEntity() {
    Parameter parameter = new Parameter();
    parameter.setId(UUID.randomUUID());
    parameter.setName("Sample Parameter");
    parameter.setLabel("Sample Label");
    return parameter;
  }
}
