package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class ParameterTypeDefinitionMapperTest {

  @Autowired private ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

  @Test
  public void testToEntity() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO =
        createSampleParameterTypeDefinitionDTO();
    ParameterTypeDefinition parameterTypeDefinition =
        parameterTypeDefinitionMapper.from(parameterTypeDefinitionDTO);

    assertEquals(parameterTypeDefinitionDTO.getId(), parameterTypeDefinition.getId());
    assertEquals(parameterTypeDefinitionDTO.getOrdering(), parameterTypeDefinition.getOrdering());
  }

  @Test
  public void testToDTO() {
    ParameterTypeDefinition parameterTypeDefinition = createSampleParameterTypeDefinitionEntity();

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO =
        parameterTypeDefinitionMapper.to(parameterTypeDefinition);

    assertEquals(parameterTypeDefinition.getId(), parameterTypeDefinitionDTO.getId());
    assertEquals(parameterTypeDefinition.getOrdering(), parameterTypeDefinitionDTO.getOrdering());
  }

  @Test
  public void testPartialUpdate() {
    ParameterTypeDefinition existingEntity = new ParameterTypeDefinition();
    existingEntity.setId(UUID.randomUUID());
    existingEntity.setOrdering(1);

    ParameterTypeDefinitionDTO updateDto = new ParameterTypeDefinitionDTO();
    updateDto.setOrdering(2);

    parameterTypeDefinitionMapper.partialUpdate(existingEntity, updateDto);

    assertEquals(2, existingEntity.getOrdering());
  }

  private ParameterTypeDefinitionDTO createSampleParameterTypeDefinitionDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.randomUUID());
    parameterTypeDefinitionDTO.setOrdering(1);
    parameterTypeDefinitionDTO.setDistributionId(UUID.randomUUID());
    return parameterTypeDefinitionDTO;
  }

  private ParameterTypeDefinition createSampleParameterTypeDefinitionEntity() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.randomUUID());
    parameterTypeDefinition.setOrdering(1);
    return parameterTypeDefinition;
  }
}
