package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.to.ParameterTypeTO;
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
class ParameterTypeMapperTest {

  @Autowired private ParameterTypeMapper parameterTypeMapper;

  @Test
  void shouldMapParameterTypeToDTO() {
    ParameterTypeTO parameterType = new ParameterTypeTO();
    UUID id = UUID.randomUUID();
    parameterType.setId(id);
    parameterType.setName("Type1");

    ParameterTypeDTO parameterTypeDTO = parameterTypeMapper.from(parameterType);

    assertEquals(id, parameterTypeDTO.getId());
    assertEquals(parameterType.getName(), parameterTypeDTO.getName());
  }

  @Test
  void shouldMapDTOToParameterTypeTO() {
    // Given
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    UUID id = UUID.randomUUID();
    parameterTypeDTO.setId(id);
    parameterTypeDTO.setName("Type1");

    ParameterTypeTO parameterTypeTO = parameterTypeMapper.to(parameterTypeDTO);

    assertEquals(id, parameterTypeTO.getId());
    assertEquals(parameterTypeDTO.getName(), parameterTypeTO.getName());
  }

  @Test
  void shouldPartiallyUpdateParameterType() {
    UUID id = UUID.randomUUID();
    String updatedName = "UpdatedType";

    ParameterTypeTO existingParameterTypeTO = new ParameterTypeTO();
    existingParameterTypeTO.setId(id);
    existingParameterTypeTO.setName("Type1");

    ParameterTypeDTO updatedParameterTypeDTO = new ParameterTypeDTO();
    updatedParameterTypeDTO.setName(updatedName);

    parameterTypeMapper.partialUpdate(updatedParameterTypeDTO, existingParameterTypeTO);
    assertEquals(updatedParameterTypeDTO.getName(), updatedParameterTypeDTO.getName());
  }
}
