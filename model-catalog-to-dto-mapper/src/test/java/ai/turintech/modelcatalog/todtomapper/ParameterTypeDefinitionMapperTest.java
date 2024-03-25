package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
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
public class ParameterTypeDefinitionMapperTest {

  @Autowired private ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

  @Test
  public void testToTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO =
        createSampleParameterTypeDefinitionDTO();
    ParameterTypeDefinitionTO parameterTypeDefinitionTO =
        parameterTypeDefinitionMapper.to(parameterTypeDefinitionDTO);

    assertEquals(parameterTypeDefinitionDTO.getId(), parameterTypeDefinitionTO.getId());
    assertEquals(parameterTypeDefinitionDTO.getOrdering(), parameterTypeDefinitionTO.getOrdering());
  }

  @Test
  public void testToDTO() {
    ParameterTypeDefinitionTO parameterTypeDefinitionTO = createSampleParameterTypeDefinitionTO();

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO =
        parameterTypeDefinitionMapper.from(parameterTypeDefinitionTO);

    assertEquals(parameterTypeDefinitionTO.getId(), parameterTypeDefinitionDTO.getId());
    assertEquals(parameterTypeDefinitionTO.getOrdering(), parameterTypeDefinitionDTO.getOrdering());
  }

  @Test
  public void testPartialUpdate() {
    ParameterTypeDefinitionTO existingTO = new ParameterTypeDefinitionTO();
    existingTO.setId(UUID.randomUUID());
    existingTO.setOrdering(1);

    ParameterTypeDefinitionDTO updateDto = new ParameterTypeDefinitionDTO();
    updateDto.setOrdering(2);

    parameterTypeDefinitionMapper.partialUpdate(updateDto, existingTO);

    assertEquals(1, existingTO.getOrdering());
  }

  private ParameterTypeDefinitionDTO createSampleParameterTypeDefinitionDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setOrdering(1);
    parameterTypeDefinitionDTO.setDistributionId(UUID.randomUUID());
    return parameterTypeDefinitionDTO;
  }

  private ParameterTypeDefinitionTO createSampleParameterTypeDefinitionTO() {
    ParameterTypeDefinitionTO parameterTypeDefinitionTO = new ParameterTypeDefinitionTO();
    parameterTypeDefinitionTO.setId(UUID.randomUUID());
    parameterTypeDefinitionTO.setOrdering(1);
    return parameterTypeDefinitionTO;
  }
}
