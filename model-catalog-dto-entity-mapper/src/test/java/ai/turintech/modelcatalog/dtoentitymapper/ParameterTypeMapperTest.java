package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
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
class ParameterTypeMapperTest {

  @Autowired private ParameterTypeMapper parameterTypeMapper;

  @Test
  void shouldMapParameterTypeToDTO() {
    ParameterType parameterType = new ParameterType();
    UUID id = UUID.randomUUID();
    parameterType.setId(id);
    parameterType.setName("Type1");

    ParameterTypeDTO parameterTypeDTO = parameterTypeMapper.to(parameterType);

    assertEquals(id, parameterTypeDTO.getId());
    assertEquals(parameterType.getName(), parameterTypeDTO.getName());
  }

  @Test
  void shouldMapDTOToParameterType() {
    // Given
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    UUID id = UUID.randomUUID();
    parameterTypeDTO.setId(id);
    parameterTypeDTO.setName("Type1");

    ParameterType parameterType = parameterTypeMapper.from(parameterTypeDTO);

    assertEquals(id, parameterType.getId());
    assertEquals(parameterTypeDTO.getName(), parameterType.getName());
  }

  @Test
  void shouldPartiallyUpdateParameterType() {
    UUID id = UUID.randomUUID();
    String updatedName = "UpdatedType";

    ParameterType existingParameterType = new ParameterType();
    existingParameterType.setId(id);
    existingParameterType.setName("Type1");

    ParameterTypeDTO updatedParameterTypeDTO = new ParameterTypeDTO();
    updatedParameterTypeDTO.setName(updatedName);

    parameterTypeMapper.partialUpdate(existingParameterType, updatedParameterTypeDTO);
    assertEquals(updatedName, existingParameterType.getName());
  }
}
